/**
 * 
 */
package org.jdesktop.swingx.autocomplete;

import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;
import javax.swing.text.Element;

/**
 * @author Karl George Schaefer
 */
final class DelegatingDocumentEvent implements DocumentEvent {

	public DelegatingDocumentEvent(Document resourcedDocument1, DocumentEvent sourceEvent1) {
		this.resourcedDocument = resourcedDocument1;
		this.sourceEvent = sourceEvent1;
	}

	/**
	 * {@inheritDoc}
	 */
	public ElementChange getChange(Element elem) {
		return this.sourceEvent.getChange(elem);
	}

	/**
	 * {@inheritDoc}
	 */
	public Document getDocument() {
		return this.resourcedDocument;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getLength() {
		return this.sourceEvent.getLength();
	}

	/**
	 * {@inheritDoc}
	 */
	public int getOffset() {
		return this.sourceEvent.getOffset();
	}

	/**
	 * {@inheritDoc}
	 */
	public EventType getType() {
		return this.sourceEvent.getType();
	}

	private final Document		resourcedDocument;

	private final DocumentEvent	sourceEvent;

}
