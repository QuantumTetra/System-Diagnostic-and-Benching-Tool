package stressTests;

import java.io.IOException;

public class CPUNameWindows {
	public static String getInfo(){
		String commandOutput = null;
		try {
			commandOutput = PowerShellCommand.runShellCommand("powershell.exe  Get-WmiObject win32_processor");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int pos = commandOutput.indexOf("Name");
		int pos2 = commandOutput.indexOf("SocketDesignation");
		
		String info = commandOutput.substring(pos, pos2);
		
		String name = info.substring(info.indexOf(':') + 2, info.length() - 1);
		
		return name;
	}
}
