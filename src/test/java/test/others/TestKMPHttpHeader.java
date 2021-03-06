package test.others;

import java.io.File;

import com.gifisan.nio.Encoding;
import com.gifisan.nio.common.FileUtil;
import com.gifisan.nio.common.KMPByteUtil;
import com.gifisan.nio.common.test.ITest;
import com.gifisan.nio.common.test.ITestHandle;

public class TestKMPHttpHeader {

	
	public static void main(String[] args) throws Exception {
		
		final KMPByteUtil	KMP_HEADER		= new KMPByteUtil("\r\n\r\n".getBytes());
		
		File file = new File("test.header");
		
		String content = FileUtil.readFileToString(file, Encoding.UTF8);
		
		final byte [] array = content.getBytes();
		
		ITestHandle.doTest(new ITest() {
			
			public void test() throws Exception {
				
				KMP_HEADER.match(array);
			}
		}, 1000000, "kmp-http-header");
	}
}
