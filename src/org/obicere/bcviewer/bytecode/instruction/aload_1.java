package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class aload_1 extends Instruction {

    private static final String MNEMONIC = "aload_1";
    private static final int    OPCODE   = 0x2b;

    public aload_1() {
        super(MNEMONIC, OPCODE);
    }

}