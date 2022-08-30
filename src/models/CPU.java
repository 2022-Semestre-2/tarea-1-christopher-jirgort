/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import javax.swing.JOptionPane;

/**
 *
 * @author Chris & Jirgort
 */
public class CPU {

  private Memory memory;
  private Register PCRegister;
  private Register IRRegister;
  private Register ACRegister;
  private Register AXRegister;
  private Register BXRegister;
  private Register CXRegister;
  private Register DXRegister;

  private final String LOAD = "0001";
  private final String STORE = "0010";
  private final String MOV = "0011";
  private final String SUB = "0100";
  private final String ADD = "0101";

  public CPU(Memory memory) {
    this.memory = memory;
    this.PCRegister = new Register("PC", "PC", 0);
    this.IRRegister = new Register("IR", "IR", 0);
    this.ACRegister = new Register("AC", "AC", 0);
    this.AXRegister = new Register("AX", "0001", 0);
    this.BXRegister = new Register("BX", "0010", 0);
    this.CXRegister = new Register("CX", "0011", 0);
    this.DXRegister = new Register("DX", "0100", 0);
  }

  public Memory getMemory() {
    return memory;
  }

  private Register getPCRegister() {
    return PCRegister;
  }

  private Register getIRRegister() {
    return IRRegister;
  }

  private Register getACRegister() {
    return ACRegister;
  }

  private Register getAXRegister() {
    return AXRegister;
  }

  private Register getBXRegister() {
    return BXRegister;
  }

  private Register getCXRegister() {
    return CXRegister;
  }

  private Register getDXRegister() {
    return DXRegister;
  }

  public void setPCRegisterVal(int val, boolean accumulative) {
    this.PCRegister.setCurrentValue(val, accumulative);
  }

  public void setIRRegisterVal(int val, boolean accumulative) {
    this.IRRegister.setCurrentValue(val, accumulative);
  }

  public void setACRegisterVal(int val, boolean accumulative) {
    this.ACRegister.setCurrentValue(val, accumulative);
  }

  public void setAXRegisterVal(int val, boolean accumulative) {
    this.AXRegister.setCurrentValue(val, accumulative);
  }

  public void setBXRegisterVal(int val, boolean accumulative) {
    this.BXRegister.setCurrentValue(val, accumulative);
  }

  public void setCXRegisterVal(int val, boolean accumulative) {
    this.CXRegister.setCurrentValue(val, accumulative);
  }

  public void setDXRegisterVal(int val, boolean accumulative) {
    this.DXRegister.setCurrentValue(val, accumulative);
  }

  public Register getRegister(String id) {

    Register register = null;
    switch (id) {
      case "PC":
        register = getPCRegister();
        break;
      case "IR":
        register = getIRRegister();
        break;
      case "AC":
        register = getACRegister();
        break;
      case "0001": // AX
        register = getAXRegister();
        break;
      case "0010": // BX
        register = getBXRegister();
        break;
      case "0011": // CX
        register = getCXRegister();
        break;
      case "0100": // DX
        register = getDXRegister();
        break;
    }
    return register;
  }

  public boolean loadProgram(Program program) {

    if (this.memory.mountProgram(program)) {

      return true;
    }
    return false;
  }

  public void executeProgram(int i) {
    CharSequence[] instruction = this.memory.getInstructionToExecute(i);
    if (instruction.length == 0) {
      JOptionPane.showMessageDialog(null, "ALL INSTRUCTIONS WERE EXECUTED");
    } else {
      CharSequence operation = instruction[0];
      CharSequence register = instruction[1];
      CharSequence value = instruction[2];
      Register operatedRegister = null;
      int registerVal = 0;
      switch (operation.toString()) {

        case LOAD:
          operatedRegister = getRegister(register.toString());
          registerVal = this.ACRegister.getCurrentValue();
          operatedRegister.setCurrentValue(registerVal, false);
          setPCRegisterVal(i + 1, false);
          setIRRegisterVal(i, false);
          break;
        case STORE:
          operatedRegister = getRegister(register.toString());
          //operatedRegister.setCurrentValue(this.ACRegister.getCurrentValue(), false);
          setACRegisterVal(operatedRegister.getCurrentValue() , false);
          setPCRegisterVal(i + 1, false);
          setIRRegisterVal(i, false);
          break;
        case MOV:
          operatedRegister = getRegister(register.toString());
          int intUnsignedVal = 0;
          if (value.toString().charAt(0) == '1') {
            String unsignedVal = value.toString().replaceFirst("1", "0");
            intUnsignedVal = Integer.parseInt(unsignedVal, 2) * -1;
          } else {
            intUnsignedVal = Integer.parseInt(value.toString(), 2);
          }
          operatedRegister.setCurrentValue(intUnsignedVal, false);
          setPCRegisterVal(i + 1, false);
          setIRRegisterVal(i, false);
          break;
        case SUB:
          operatedRegister = getRegister(register.toString());
          registerVal = operatedRegister.getCurrentValue();
          setACRegisterVal(registerVal, true);
          setPCRegisterVal(i + 1, false);
          setIRRegisterVal(i, false);
          break;
        case ADD:
          operatedRegister = getRegister(register.toString());
          registerVal = operatedRegister.getCurrentValue();
          setACRegisterVal(registerVal, true);
          setPCRegisterVal(i + 1, false);
          setIRRegisterVal(i, false);
          break;
        default:
          break;
      }
    }

  }

}
