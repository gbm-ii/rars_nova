package rars.riscv.lang.lexing;

import org.jetbrains.annotations.NotNull;
import rars.ErrorList;
import rars.ErrorMessage;
import rars.RISCVProgram;
import rars.riscv.lang.Position;

import javax.swing.text.Segment;
import java.util.ArrayList;
import java.util.List;

public class RVTokenBuilder implements TokenBuilder<TokenizedLine> {
    private final Lexer<TokenizedLine, RVTokenBuilder> lexer;
    private final @NotNull RISCVProgram program;
    private final @NotNull ErrorList errorList;
    private @NotNull ArrayList<@NotNull RVToken> result;
    private int lineNum;

    public RVTokenBuilder(
        final @NotNull Lexer<TokenizedLine, RVTokenBuilder> lexer,
        final @NotNull RISCVProgram program,
        final @NotNull ErrorList errorList
    ) {
        this.lexer = lexer;
        this.program = program;
        this.errorList = errorList;
        this.result = new ArrayList<>();
    }

    public RVTokenBuilder(
        final @NotNull RISCVProgram program,
        final @NotNull ErrorList errorList
    ) {
        this(new RVLexer<>(), program, errorList);
    }

    @Override
    public @NotNull TokenizedLine getEmptyResult() {
        return new TokenizedLine(List.of(), lineNum);
    }

    @Override
    public TokenizedLine getTokenList(
        final Segment text, final int initialTokenType, final int lineOffset,
        final int lineNum
    ) {
        this.result = new ArrayList<>();
        this.lineNum = lineNum;
        return lexer.getTokensList(text, initialTokenType, lineOffset, this);
    }

    @Override
    public void addToken(
        final char @NotNull [] tokenizedChars,
        final int startOffset,
        final int endOffset,
        final @NotNull RVTokenType tokenType,
        final int lineOffset
    ) {
        final var position = new Position(this.lineNum, startOffset, lineOffset);
        final var substring = new String(tokenizedChars, startOffset, endOffset - startOffset);
        final var newToken = new RVToken(position, tokenType, substring);
        result.add(newToken);
    }

    @Override
    public void addErrorToken(
        final char[] tokenizedChars,
        final int offset,
        final int lineOffset,
        final String errorMessage
    ) {
        this.errorList.add(ErrorMessage.error(this.program, this.lineNum, lineOffset, errorMessage));
        TokenBuilder.super.addErrorToken(tokenizedChars, offset, lineOffset, errorMessage);
    }

    @Override
    public @NotNull TokenizedLine getResult() {
        return new TokenizedLine(result, lineNum);
    }
}
