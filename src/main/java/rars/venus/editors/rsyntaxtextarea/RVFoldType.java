package rars.venus.editors.rsyntaxtextarea;

import org.fife.ui.rsyntaxtextarea.folding.FoldType;

public final class RVFoldType implements FoldType {
    private RVFoldType() {
    }

    public static final int
        REGION = FoldType.FOLD_TYPE_USER_DEFINED_MIN + 1,
        LABEL = REGION + 1,
        INSTRUCTION_BLOCK = LABEL + 1,
        MACRO = INSTRUCTION_BLOCK + 1;

}
