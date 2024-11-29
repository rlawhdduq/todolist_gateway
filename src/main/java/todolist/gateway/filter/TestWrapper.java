package todolist.gateway.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class TestWrapper extends HttpServletRequestWrapper {
    
    private final byte[] cacheBody;

    public TestWrapper(HttpServletRequest request) throws IOException {
        super(request);

        InputStream inputStrem = request.getInputStream();
        this.cacheBody = inputStrem.readAllBytes();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(cacheBody);
        return new ServletInputStream() {
            @Override 
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }

            @Override
            public boolean isFinished(){
                return byteArrayInputStream.available() == 0;
            }

            @Override
            public void setReadListener(ReadListener readListener){
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean isReady() {
                return true;
            }
        };
    }

    public byte[] getCachedBody(){
        return cacheBody;
    }
}
