package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class lload extends Instruction {

    private static final String MNEMONIC = "lload";
    private static final int    OPCODE   = 0x16;

    private final int index;

    public lload(final int index) {
        super(MNEMONIC, OPCODE);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}