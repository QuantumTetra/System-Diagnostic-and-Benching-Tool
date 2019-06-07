package stressTests;

import java.util.ArrayList;

public class DiskNamesWindows {
	public static void main(String[]args){
		String info = info();
		System.out.println(info);
	}
	public static String info(){
		String answer = null;
		try {
			answer = PowerShellCommand.runShellCommand("powershell.exe  Get-WmiObject Win32_LogicalDisk");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] array = answer.split("\n");
		ArrayList<String> array2 = new ArrayList<>();
		for(int i = 0; i < array.length; i++){
			if(!array[i].equals("")){
				array2.add(array[i]);
			}
		}
		/*for(int i = 0; i < 6; i++){
			array2.remove(array2.size() - 1);
		}*/
		String[] a3 = new String[array2.size()];
		for(int i = 0; i < a3.length ; i++){
			a3[i] = array2.get(i);
		}
		int num = a3.length / 6;
		String ans = "";
		for(int i = 0; i < num; i++){
			int pos = i*6;
			if(!a3[pos].substring(15).equals("") && !a3[pos + 3].substring(15).equals("") && !a3[pos + 4].substring(15).equals("") && !a3[pos + 5].substring(15).equals("")){
				String diskName = a3[pos + 0].substring(15).replaceAll(" ", "") + " " +a3[pos + 5].substring(15);
				String diskSpaceTotal = " Total Space: " + (Long.parseLong(a3[pos + 4].substring(15).replaceAll(" ", ""))/1073741824) + "GB";
				String diskSpaceFree = " Free Space: " + (Long.parseLong(a3[pos + 3].substring(15).replaceAll(" ", ""))/1073741824) + "GB\n";
				ans = ans + diskName + diskSpaceTotal + diskSpaceFree;
			}
		}

		return ans;
	}
}
