// GIFAnimWriterInterface.java//// Jack Boyce (jboyce@users.sourceforge.net)package gifwriter;import java.io.*;import java.awt.*;public interface GIFAnimWriterInterface {	public void doColorMap(Color color, boolean defaultcolor) throws IOException;	public void doColorMap(Color[] colorarray) throws IOException;	public void doColorMap(Color color) throws IOException;	//	public IntHashtable getColorMap();		public void writeHeader(OutputStream out) throws IOException;	public void writeDelay(int delay, OutputStream out) throws IOException;	public void writeGIF(Image img, OutputStream out) throws IOException;	public void writeTrailer(OutputStream out) throws IOException;}