/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Chris & Jirgort
 */
public class Memory {

  private int size;
  private String[] stack;
  private int unallocatedMemory;
  private Program program;
  private int programIndex;
  
  private final String LOAD = "LOAD";
  private final String STORE = "STORE";
  private final String MOV = "MOV";
  private final String SUB = "SUB";
  private final String ADD = "ADD";

  public Memory(int size) {
    this.size = size;
    this.stack = new String[size];
    this.unallocatedMemory = size - 10;
    this.programIndex = getRandomMemorySpace(10, unallocatedMemory);
    fillInstructions();
  }

  public int getSize() {
    return size;
  }

  public String[] getStack() {
    return stack;
  }

  public int getUnallocatedMemory() {
    return unallocatedMemory;
  }

  public Program getProgram() {
    return program;
  }
  
  public int getProgramIndex() {
    return programIndex;
  }
  
  public boolean mountProgram(Program program) {

    this.program = program;
    if (this.unallocatedMemory > program.getSize()) {
      //int j = 0;
      int j = this.programIndex;
      for (int i = 0; i < program.getSize(); i++) {
        String[] instruction = program.getNextInstruction();
        String operation = instruction[0];
        String register = instruction[1];
        String value = instruction[2];
        String binaryInst = "";
        switch(operation) {
          case LOAD:
            binaryInst = "0001";
            binaryInst += getRegisterID(register) + "";
            binaryInst += "00000000";
            break;
          case STORE:
            binaryInst = "0010";
            binaryInst += getRegisterID(register) + "";
            binaryInst += "00000000";
            break;
          case MOV:
            binaryInst = "0011";
            binaryInst += getRegisterID(register) + "";
            boolean sign = value.contains("-");
            value = Integer.parseInt(value) < 0? String.valueOf(Integer.parseInt(value) * -1) : String.valueOf(Integer.parseInt(value));
            binaryInst += fillBits(String.valueOf(Integer.toBinaryString(Integer.parseInt(value))), sign);
            break;
          case SUB:
            binaryInst = "0100";
            binaryInst += getRegisterID(register) + "";
            binaryInst += "00000000";
            break;
          case ADD:
            binaryInst = "0101";
            binaryInst += getRegisterID(register) + "";
            binaryInst += "00000000";
            break;
          default:
            break;
        }
        stack[j] = binaryInst;
        j++;
      }
      return true;
    }
    return false;
  }

  public int getRandomMemorySpace(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
  }
  
  private String getRegisterID(String register) {
  
    String res;
    switch(register) {
    
      case "AX":
        res = "0001";
        break;
      case "BX":
        res = "0010";
        break;
      case "CX":
        res = "0011";
        break;
      case "DX":
        res = "0100";
        break;
      default:
        res = "";
    }
    return res;
  }

  private String fillBits(String binaryNum, boolean sign) {

    int zeros = 8 - binaryNum.length();
    int i = 0;
    String newBinaryNum = "";
    while (i < zeros) {
      if (sign) {
        newBinaryNum += "1";
        sign = false;
      } else {
        newBinaryNum += "0";
      }
      i++;
    }
    return newBinaryNum.concat(binaryNum);
  }

  public void fillInstructions() {

    for (int i = 0; i < this.stack.length; i++) {
      this.stack[i] = "0";
    }
  }

  public CharSequence[] getInstructionToExecute(int i) {

    int limit = this.getProgramIndex() + this.program.getSize();
    if (i >= limit) {
      CharSequence[] inst = {};
      return inst;
    } else {
      String instruction = this.stack[i];
      //String[] inst = instruction.split("  ");
      CharSequence operator = instruction.subSequence(0, 4);
      CharSequence register = instruction.subSequence(4, 8);
      CharSequence value = instruction.subSequence(8, 16);
      CharSequence[] inst = {operator, register, value};
      return inst;
    }
  }
  
}
