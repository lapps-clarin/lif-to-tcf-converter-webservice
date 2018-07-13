package de.tuebingen.uni.sfs.lapps.lifconverter.resources;

import de.tuebingen.uni.sfs.lapps.lifconverter.utils.StreamingOutputExtended;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StreamingTempFileOutput implements StreamingOutputExtended {

    private final File file;

    public StreamingTempFileOutput(File file) {
        this.file = file;
    }

    @Override
    public void write(OutputStream out) throws IOException, WebApplicationException {
        FileInputStream input = null;
        byte[] buffer = new byte[256 * 1024];
        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
        try {
            input = new FileInputStream(file);
            FileChannel channel = input.getChannel();
            for (int length = 0; (length = channel.read(byteBuffer)) != -1;) {
                out.write(buffer, 0, length);
                byteBuffer.clear();
            }
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    Logger.getLogger(StreamingTempFileOutput.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            file.delete();
        }
    }

    @Override
    public File getFile() {
        return file;
    }

}
