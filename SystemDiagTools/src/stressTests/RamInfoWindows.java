package stressTests;

import java.io.IOException;
import java.util.ArrayList;

public class RamInfoWindows {
	public static void main(String[]args){
		String[] physicalRamSticks = info().split("\n");
		for(int i = 0; i < physicalRamSticks.length; i++){
			System.out.println(physicalRamSticks[i]);
		}
	}
	public static String info(){
		String returnString = null;
		try {
			returnString = PowerShellCommand.runShellCommand("powershell.exe  Get-WmiObject Win32_PhysicalMemory");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<String> Strings = new ArrayList<>(); 
		String[] powerOutput = returnString.split("\n");
		for(int i = 0; i < powerOutput.length; i++){
			if(powerOutput[i].length() > 22){
				if(powerOutput[i].substring(0, 22).contains("Capacity")){
					Strings.add(powerOutput[i].substring(23));
				}
				if(powerOutput[i].substring(0, 22).contains("ConfiguredClockSpeed")){
					Strings.add(powerOutput[i].substring(23));
				}
				if(powerOutput[i].substring(0, 22).contains("Manufacturer")){
					Strings.add(powerOutput[i].substring(23));
				}
				if(powerOutput[i].substring(0, 22).contains("PartNumber")){
					Strings.add(powerOutput[i].substring(23));
				}
			}
		}
		String[] finalArray = new String[16];
		for(int i = 0; i < Strings.size() / 4; i++){
			finalArray[i] = Strings.get(i * 4 + 2)+ " " + Strings.get(i * 4 + 1) + "MHz " + (Long.parseLong(Strings.get(i * 4 + 0))/1073741824) + "GB " + Strings.get(i * 4 + 3);
		}
		String finish = "";
		for(int i = 0; i < finalArray.length; i++){
			if(finalArray[i]!=null){
				finish = finish + "" + finalArray[i] + "\n";
			}
		}
		return finish;
	}
}
