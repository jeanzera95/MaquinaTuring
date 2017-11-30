/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turingmachine;

/**
 *
 * @author jean_
 */

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;

import java.io.*;

public class Machine {
	private String tape;
	HashMap<String,Transition> transitions;
	
	public static void main(String[] args){
		Machine maquina = new Machine("aa");
		maquina.run();
	}
	public void run(){
		try{
			int i = 0;
			char read;
			Object[] transitionsKeys = transitions.keySet().toArray();
			Transition transition = transitions.get(transitionsKeys[0]);
			System.out.println("Fita (Entrada): "+tape);
			do{
					read = tape.charAt(i);
					String writeAndTransition = transition.run(read);
					System.out.println(writeAndTransition);
					String[] token = writeAndTransition.split("/");
					if(!token[0].equals("#")){changeTape(token[0].charAt(0),i);}
					transition = transitions.get(token[1]);
					i+=Integer.parseInt(token[2]);
			}while(i<tape.length());
			
			System.out.println("Fita (Saída): "+tape);
			System.out.println("Estado: "+transition.getCurrentState());
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	public Machine(String tape){
		this.tape = tape;
		loadFile("src/files/transitions.csv");
	}
	
	public Machine(){
		loadFile("src/files/transitions.csv");
	}
	
	public void setTape(String tape){
		this.tape = tape;
	}
	
	public void changeTape(char c, int pos){
		StringBuilder aux = new StringBuilder(tape);
		aux.setCharAt(pos, c);
		tape = aux.toString();
	}
	
	public void loadFile(String fileName){
		try{
			transitions = new HashMap<String,Transition>();
			BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
			String linha = "";
			while((linha = reader.readLine()) != null){
				boolean set = false;
				String[] tokens = linha.split(";");
				Object[] transitionsKeys = transitions.keySet().toArray();
				for(Object key : transitionsKeys){
					if(tokens[0].equals(key)){
						set = true;
						break;
					}
				}
				if(set){
					transitions.get(tokens[0]).addFunction(new Function(tokens[1].charAt(0),tokens[3].charAt(0),tokens[2],Integer.parseInt(tokens[4])));
				}else{
					transitions.put(tokens[0],new Transition(tokens[0],tokens[1].charAt(0),tokens[2],tokens[3].charAt(0),Integer.parseInt(tokens[4])));				
				}
			}
			reader.close();
		}catch(IOException ex){
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}
}
    

