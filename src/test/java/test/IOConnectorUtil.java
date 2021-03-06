package test;

import com.gifisan.nio.common.CloseUtil;
import com.gifisan.nio.common.LoggerFactory;
import com.gifisan.nio.component.DefaultNIOContext;
import com.gifisan.nio.component.IOEventHandleAdaptor;
import com.gifisan.nio.component.LoggerSEListener;
import com.gifisan.nio.component.NIOContext;
import com.gifisan.nio.connector.TCPConnector;
import com.gifisan.nio.extend.ConnectorCloseSEListener;
import com.gifisan.nio.extend.SessionActiveSEListener;
import com.gifisan.nio.extend.SimpleIOEventHandle;
import com.gifisan.nio.extend.configuration.ServerConfiguration;

public class IOConnectorUtil {

	public static TCPConnector getTCPConnector(IOEventHandleAdaptor ioEventHandleAdaptor) {
		return getTCPConnector(ioEventHandleAdaptor, null);
	}

	public static TCPConnector getTCPConnector(IOEventHandleAdaptor ioEventHandleAdaptor,
			ServerConfiguration configuration) {
		
		TCPConnector connector = null;

		try {

			connector = new TCPConnector();

			NIOContext context = new DefaultNIOContext();

			context.setServerConfiguration(configuration);

			context.setIOEventHandleAdaptor(ioEventHandleAdaptor);

			context.addSessionEventListener(new LoggerSEListener());

			context.addSessionEventListener(new ConnectorCloseSEListener(connector));
			
			if (ioEventHandleAdaptor instanceof SimpleIOEventHandle) {
				
				SimpleIOEventHandle handle = (SimpleIOEventHandle) ioEventHandleAdaptor;
				
				context.addSessionEventListener(new SessionActiveSEListener(handle.getFixedSession()));
			}
			
			connector.setContext(context);

			return connector;

		} catch (Throwable e) {

			LoggerFactory.getLogger(IOConnectorUtil.class).error(e.getMessage(), e);

			CloseUtil.close(connector);

			throw new RuntimeException(e);
		}
	}
}
