package stressTests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PowerShellCommand {
	public static void main(String[]args){
		try {
			System.out.println(runShellCommand("powershell.exe  Get-WmiObject win32_processor"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String runShellCommand(String command) throws IOException {
		String returnString = "";
		//String command = "powershell.exe  your command";
		//Getting the version
		// Executing the command
		Process powerShellProcess = Runtime.getRuntime().exec(command);
		// Getting the results
		powerShellProcess.getOutputStream().close();
		String line;
		//System.out.println("Standard Output:");
		BufferedReader stdout = new BufferedReader(new InputStreamReader(
				powerShellProcess.getInputStream()));
		while ((line = stdout.readLine()) != null) {
			returnString = returnString + "" + line + "\n";
			//System.out.println(line);
		}
		stdout.close();
		//System.out.println("Standard Error:");
		BufferedReader stderr = new BufferedReader(new InputStreamReader(
				powerShellProcess.getErrorStream()));
		while ((line = stderr.readLine()) != null) {
			//System.out.println(line);
		}
		stderr.close();
		return returnString;

	}

}