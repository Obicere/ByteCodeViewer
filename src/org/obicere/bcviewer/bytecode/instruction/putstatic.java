package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class putstatic extends Instruction {

    private static final String MNEMONIC = "putstatic";
    private static final int    OPCODE   = 0xb3;

    private final int indexbyte1;
    private final int indexbyte2;

    public putstatic(final int indexbyte1, final int indexbyte2) {
        super(MNEMONIC, OPCODE);
        this.indexbyte1 = indexbyte1;
        this.indexbyte2 = indexbyte2;
    }

    public int getIndexbyte1() {
        return indexbyte1;
    }

    public int getIndexbyte2() {
        return indexbyte2;
    }

    public int getIndex() {
        return (indexbyte1 << 8) | indexbyte2;
    }
}
