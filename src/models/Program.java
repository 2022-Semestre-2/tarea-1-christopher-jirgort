/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Chris & Jirgort
 */
public class Program {

  private int size;
  private String[] instructions;
  private int currentLine;

  public Program(int size, String[] instructions) {
    this.size = size;
    this.instructions = instructions;
  }

  public int getSize() {
    return size;
  }

  public String[] getNextInstruction() {
    //for(String val : instructions) {
    //  System.out.println("getNextInstruction: " + val);
    //}
    String[] instr = {"", "", ""};
    instr[0] = this.instructions[currentLine];
    currentLine += 1;
    instr[1] = this.instructions[currentLine];
    currentLine += 1;
    instr[2] = this.instructions[currentLine];
    currentLine += 1;
    return instr;
  }

  public int getCurrentLine() {
    return currentLine;
  }

}
