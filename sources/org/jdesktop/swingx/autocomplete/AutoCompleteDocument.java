/*
 * $Id: AutoCompleteDocument.java 3365 2009-06-17 01:52:57Z kschaefe $
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 */
package org.jdesktop.swingx.autocomplete;

import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.EventListenerList;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.PlainDocument;
import javax.swing.text.Position;
import javax.swing.text.Segment;
import org.jdesktop.swingx.util.Contract;

/**
 * A document that can be plugged into any JTextComponent to enable automatic completion.
 * It finds and selects matching items using any implementation of the AbstractAutoCompleteAdaptor.
 */
public class AutoCompleteDocument implements Document {

	/**
	 * Creates a new AutoCompleteDocument for the given AbstractAutoCompleteAdaptor.
	 * 
	 * @param strictMatching
	 *            true, if only items from the adaptor's list should
	 *            be allowed to be entered
	 * @param adaptor
	 *            The adaptor that will be used to find and select matching
	 *            items.
	 */
	public AutoCompleteDocument(AbstractAutoCompleteAdaptor adaptor1, boolean strictMatching1) {
		this(adaptor1, strictMatching1, null);
	}

	/**
	 * Creates a new AutoCompleteDocument for the given AbstractAutoCompleteAdaptor.
	 * 
	 * @param adaptor
	 *            The adaptor that will be used to find and select matching
	 *            items.
	 * @param strictMatching
	 *            true, if only items from the adaptor's list should
	 *            be allowed to be entered
	 * @param stringConverter
	 *            the converter used to transform items to strings
	 */
	public AutoCompleteDocument(AbstractAutoCompleteAdaptor adaptor1, boolean strictMatching1, ObjectToStringConverter stringConverter) {
		this(adaptor1, strictMatching1, stringConverter, null);
	}

	/**
	 * Creates a new AutoCompleteDocument for the given AbstractAutoCompleteAdaptor.
	 * 
	 * @param adaptor
	 *            The adaptor that will be used to find and select matching
	 *            items.
	 * @param strictMatching
	 *            true, if only items from the adaptor's list should
	 *            be allowed to be entered
	 * @param stringConverter
	 *            the converter used to transform items to strings
	 * @param delegate
	 *            the {@code Document} delegate backing this document
	 */
	@SuppressWarnings("synthetic-access") public AutoCompleteDocument(	AbstractAutoCompleteAdaptor adaptor1,
																		boolean strictMatching1,
																		ObjectToStringConverter stringConverter,
																		Document delegate) {
		this.adaptor = Contract.asNotNull(adaptor1, "adaptor cannot be null");
		this.strictMatching = strictMatching1;
		this.objGobjectToStringConverter = stringConverter == null ? ObjectToStringConverter.DEFAULT_IMPLEMENTATION : stringConverter;
		this.objGdelegateDocument = delegate == null ? this.createDefaultDocument() : delegate;

		this.handler = new Handler();
		this.objGdelegateDocument.addDocumentListener(this.handler);

		// Handle initially selected object
		final Object selected = adaptor1.getSelectedItem();
		if (selected != null) {
			this.setText(this.objGobjectToStringConverter.getPreferredStringForItem(selected));
		}
		this.adaptor.markEntireText();
	}

	/**
	 * {@inheritDoc}
	 */
	public void addDocumentListener(DocumentListener listener) {
		this.handler.addDocumentListener(listener);
	}

	/**
	 * {@inheritDoc}
	 */
	public void addUndoableEditListener(UndoableEditListener listener) {
		this.handler.addUndoableEditListener(listener);
	}

	/**
	 * {@inheritDoc}
	 */
	public Position createPosition(int offs) throws BadLocationException {
		return this.objGdelegateDocument.createPosition(offs);
	}

	/**
	 * {@inheritDoc}
	 */
	public Element getDefaultRootElement() {
		return this.objGdelegateDocument.getDefaultRootElement();
	}

	/**
	 * {@inheritDoc}
	 */
	public Position getEndPosition() {
		return this.objGdelegateDocument.getEndPosition();
	}

	/**
	 * {@inheritDoc}
	 */
	public int getLength() {
		return this.objGdelegateDocument.getLength();
	}

	/**
	 * {@inheritDoc}
	 */
	public Object getProperty(Object key) {
		return this.objGdelegateDocument.getProperty(key);
	}

	/**
	 * {@inheritDoc}
	 */
	public Element[] getRootElements() {
		return this.objGdelegateDocument.getRootElements();
	}

	/**
	 * {@inheritDoc}
	 */
	public Position getStartPosition() {
		return this.objGdelegateDocument.getStartPosition();
	}

	/**
	 * {@inheritDoc}
	 */
	public String getText(int offset, int length) throws BadLocationException {
		return this.objGdelegateDocument.getText(offset, length);
	}

	/**
	 * {@inheritDoc}
	 */
	public void getText(int offset, int length, Segment txt) throws BadLocationException {
		this.objGdelegateDocument.getText(offset, length, txt);
	}

	public void insertString(int off, String str, AttributeSet a) throws BadLocationException {
		// return immediately when selecting an item
		if (this.bolGselecting) {
			return;
		}
		// insert the string into the document
		this.objGdelegateDocument.insertString(off, str, a);
		// lookup and select a matching item
		LookupResult lookupResult;
		final String pattern = this.getText(0, this.getLength());
		int offs = off;

		if (pattern == null || pattern.length() == 0) {
			lookupResult = new LookupResult(null, "");
			this.setSelectedItem(lookupResult.objGmatchingItemObject, lookupResult.objGmatchingString);
		} else {
			lookupResult = this.lookupItem(pattern);
			if (lookupResult.objGmatchingItemObject != null) {
				this.setSelectedItem(lookupResult.objGmatchingItemObject, lookupResult.objGmatchingString);
			} else {
				if (this.strictMatching) {
					// keep old item selected if there is no match
					lookupResult.objGmatchingItemObject = this.adaptor.getSelectedItem();
					lookupResult.objGmatchingString = this.adaptor.getSelectedItemAsString();
					// imitate no insert (later on offs will be incremented by
					// str.length(): selection won't move forward)
					offs = off - str.length();
					// provide feedback to the user that his input has been received but can not be accepted
					UIManager.getLookAndFeel().provideErrorFeedback(this.adaptor.getTextComponent());
				} else {
					// no item matches => use the current input as selected item
					lookupResult.objGmatchingItemObject = this.getText(0, this.getLength());
					lookupResult.objGmatchingString = this.getText(0, this.getLength());
					this.setSelectedItem(lookupResult.objGmatchingItemObject, lookupResult.objGmatchingString);
				}
			}
		}
		this.setText(lookupResult.objGmatchingString);
		// select the completed part
		this.adaptor.markText(offs + str.length());
	}

	/**
	 * Returns if only items from the adaptor's list should be allowed to be entered.
	 * 
	 * @return if only items from the adaptor's list should be allowed to be entered
	 */
	public boolean isStrictMatching() {
		return this.strictMatching;
	}

	/**
	 * {@inheritDoc}
	 */
	public void putProperty(Object key, Object value) {
		this.objGdelegateDocument.putProperty(key, value);
	}

	public void remove(int offs, int len) throws BadLocationException {
		// return immediately when selecting an item
		if (this.bolGselecting) {
			return;
		}
		this.objGdelegateDocument.remove(offs, len);
		if (!this.strictMatching) {
			this.setSelectedItem(this.getText(0, this.getLength()), this.getText(0, this.getLength()));
			this.adaptor.getTextComponent().setCaretPosition(offs);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void removeDocumentListener(DocumentListener listener) {
		this.handler.removeDocumentListener(listener);
	}

	/**
	 * {@inheritDoc}
	 */
	public void removeUndoableEditListener(UndoableEditListener listener) {
		this.handler.removeUndoableEditListener(listener);
	}

	/**
	 * {@inheritDoc}
	 */
	public void render(Runnable r) {
		this.objGdelegateDocument.render(r);
	}

	/**
	 * Creates the default backing document when no delegate is passed to this
	 * document.
	 * 
	 * @return the default backing document
	 */
	protected Document createDefaultDocument() {
		return new PlainDocument();
	}

	/**
	 * Searches for an item that matches the given pattern. The AbstractAutoCompleteAdaptor
	 * is used to access the candidate items. The match is not case-sensitive
	 * and will only match at the beginning of each item's string representation.
	 * 
	 * @param pattern
	 *            the pattern that should be matched
	 * @return the first item that matches the pattern or <code>null</code> if no item matches
	 */
	private LookupResult lookupItem(String pattern) {
		final Object selectedItem = this.adaptor.getSelectedItem();

		String[] possibleStrings;

		// iterate over all items to find an exact match
		for (int i = 0, n = this.adaptor.getItemCount(); i < n; i++) {
			final Object currentItem = this.adaptor.getItem(i);
			possibleStrings = this.objGobjectToStringConverter.getPossibleStringsForItem(currentItem);
			if (possibleStrings != null) {
				// current item exactly matches the pattern?
				for (final String possibleString : possibleStrings) {
					if (possibleString.equalsIgnoreCase(pattern)) {
						return new LookupResult(currentItem, possibleString);
					}
				}
			}
		}
		// check if the currently selected item matches
		possibleStrings = this.objGobjectToStringConverter.getPossibleStringsForItem(selectedItem);
		if (possibleStrings != null) {
			for (final String possibleString : possibleStrings) {
				if (this.startsWithIgnoreCase(possibleString, pattern)) {
					return new LookupResult(selectedItem, possibleString);
				}
			}
		}
		// search for any matching item, if the currently selected does not match
		for (int i = 0, n = this.adaptor.getItemCount(); i < n; i++) {
			final Object currentItem = this.adaptor.getItem(i);
			possibleStrings = this.objGobjectToStringConverter.getPossibleStringsForItem(currentItem);
			if (possibleStrings != null) {
				for (final String possibleString : possibleStrings) {
					if (this.startsWithIgnoreCase(possibleString, pattern)) {
						return new LookupResult(currentItem, possibleString);
					}
				}
			}
		}
		// no item starts with the pattern => return null
		return new LookupResult(null, "");
	}

	/**
	 * Selects the given item using the AbstractAutoCompleteAdaptor.
	 * 
	 * @param itemAsString
	 *            string representation of the item to be selected
	 * @param item
	 *            the item that is to be selected
	 */
	private void setSelectedItem(Object item, String itemAsString) {
		this.bolGselecting = true;
		this.adaptor.setSelectedItem(item);
		this.adaptor.setSelectedItemAsString(itemAsString);
		this.bolGselecting = false;
	}

	/**
	 * Sets the text of this AutoCompleteDocument to the given text.
	 * 
	 * @param text
	 *            the text that will be set for this document
	 */
	private void setText(String text) {
		try {
			// remove all text and insert the completed string
			this.objGdelegateDocument.remove(0, this.getLength());
			this.objGdelegateDocument.insertString(0, text, null);
		} catch (final BadLocationException e) {
			throw new RuntimeException(e.toString());
		}
	}

	/**
	 * Returns true if <code>base</code> starts with <code>prefix</code> (ignoring case).
	 * 
	 * @param base
	 *            the string to be checked
	 * @param prefix
	 *            the prefix to check for
	 * @return true if <code>base</code> starts with <code>prefix</code>; false otherwise
	 */
	// TODO entry point for handling 739
	private boolean startsWithIgnoreCase(String base, String prefix) {
		if (base.length() < prefix.length()) {
			return false;
		}
		return base.regionMatches(true, 0, prefix, 0, prefix.length());
	}

	private class Handler implements DocumentListener, UndoableEditListener {

		public void addDocumentListener(DocumentListener listener) {
			this.listenerList.add(DocumentListener.class, listener);
		}

		public void addUndoableEditListener(UndoableEditListener listener) {
			this.listenerList.add(UndoableEditListener.class, listener);
		}

		/**
		 * {@inheritDoc}
		 */
		public void changedUpdate(DocumentEvent ev) {
			final DocumentEvent e = new DelegatingDocumentEvent(AutoCompleteDocument.this, ev);

			// Guaranteed to return a non-null array
			final Object[] listeners = this.listenerList.getListenerList();
			// Process the listeners last to first, notifying
			// those that are interested in this event
			for (int i = listeners.length - 2; i >= 0; i -= 2) {
				if (listeners[i] == DocumentListener.class) {
					// Lazily create the event:
					// if (e == null)
					// e = new ListSelectionEvent(this, firstIndex, lastIndex);
					((DocumentListener) listeners[i + 1]).changedUpdate(e);
				}
			}
		}

		/**
		 * {@inheritDoc}
		 */
		public void insertUpdate(DocumentEvent ev) {
			final DocumentEvent e = new DelegatingDocumentEvent(AutoCompleteDocument.this, ev);

			// Guaranteed to return a non-null array
			final Object[] listeners = this.listenerList.getListenerList();
			// Process the listeners last to first, notifying
			// those that are interested in this event
			for (int i = listeners.length - 2; i >= 0; i -= 2) {
				if (listeners[i] == DocumentListener.class) {
					// Lazily create the event:
					// if (e == null)
					// e = new ListSelectionEvent(this, firstIndex, lastIndex);
					((DocumentListener) listeners[i + 1]).insertUpdate(e);
				}
			}
		}

		/**
		 * {@inheritDoc}
		 */
		public void removeDocumentListener(DocumentListener listener) {
			this.listenerList.remove(DocumentListener.class, listener);
		}

		/**
		 * {@inheritDoc}
		 */
		public void removeUndoableEditListener(UndoableEditListener listener) {
			this.listenerList.remove(UndoableEditListener.class, listener);
		}

		/**
		 * {@inheritDoc}
		 */
		public void removeUpdate(DocumentEvent ev) {
			final DocumentEvent e = new DelegatingDocumentEvent(AutoCompleteDocument.this, ev);

			// Guaranteed to return a non-null array
			final Object[] listeners = this.listenerList.getListenerList();
			// Process the listeners last to first, notifying
			// those that are interested in this event
			for (int i = listeners.length - 2; i >= 0; i -= 2) {
				if (listeners[i] == DocumentListener.class) {
					// Lazily create the event:
					// if (e == null)
					// e = new ListSelectionEvent(this, firstIndex, lastIndex);
					((DocumentListener) listeners[i + 1]).removeUpdate(e);
				}
			}
		}

		/**
		 * {@inheritDoc}
		 */
		public void undoableEditHappened(UndoableEditEvent ev) {
			final UndoableEditEvent e = new UndoableEditEvent(AutoCompleteDocument.this, ev.getEdit());

			// Guaranteed to return a non-null array
			final Object[] listeners = this.listenerList.getListenerList();
			// Process the listeners last to first, notifying
			// those that are interested in this event
			for (int i = listeners.length - 2; i >= 0; i -= 2) {
				if (listeners[i] == UndoableEditListener.class) {
					// Lazily create the event:
					// if (e == null)
					// e = new ListSelectionEvent(this, firstIndex, lastIndex);
					((UndoableEditListener) listeners[i + 1]).undoableEditHappened(e);
				}
			}
		}

		private final EventListenerList	listenerList	= new EventListenerList();
	}

	private static class LookupResult {

		public LookupResult(Object matchingItem, String matchingString) {
			this.objGmatchingItemObject = matchingItem;
			this.objGmatchingString = matchingString;
		}

		Object	objGmatchingItemObject;
		String	objGmatchingString;
	}

	/**
	 * The adaptor that is used to find and select items.
	 */
	AbstractAutoCompleteAdaptor	adaptor;

	/**
	 * Flag to indicate if adaptor.setSelectedItem has been called.
	 * Subsequent calls to remove/insertString should be ignored
	 * as they are likely have been caused by the adapted Component that
	 * is trying to set the text for the selected component.
	 */
	boolean						bolGselecting	= false;

	ObjectToStringConverter		objGobjectToStringConverter;

	protected final Document	objGdelegateDocument;

	/**
	 * true, if only items from the adaptors's list can be entered
	 * false, otherwise (selected item might not be in the adaptors's list)
	 */
	protected boolean			strictMatching;

	private final Handler		handler;
}
