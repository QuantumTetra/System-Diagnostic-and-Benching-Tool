package stressTests;

import java.io.IOException;

public class GPUNameWindows {
	public static String info(){
		String answer = null;
		try {
			answer = PowerShellCommand.runShellCommand("powershell.exe  wmic path win32_VideoController get name");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] array = answer.split("\n");
		answer = "";
		for(int i = 0; i < array.length; i++){
			if(!array[i].equals("") && !array[i].contains("Name")){
				answer = answer + array[i] + "\n";
			}
		}
		return answer;
	}
}
