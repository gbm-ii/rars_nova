package rars.riscv.lang.lexing;

import org.jetbrains.annotations.NotNull;

import javax.swing.text.Segment;

/**
 *
 * @param <CollectionType>
 *     the type representing a collection of tokens.
 */
public interface TokenBuilder<CollectionType> {
    /**
     * Adds a token to the end of the token list that is currently built.
     *
     * @param tokenizedChars
     *     the text of the entire document being tokenized.
     * @param startOffset
     *     the position of the first character of the token in the document.
     * @param endOffset
     *     the position of the last character of the token in the
     *     document (*not* the first character after the token).
     * @param tokenType
     *     the type of the token.
     * @param lineOffset
     *     the position of the first character of the token relative to the
     *     start of the line on which it resides.
     */
    void addToken(
        char[] tokenizedChars,
        int startOffset,
        int endOffset,
        @NotNull RVTokenType tokenType,
        int lineOffset
    );

    /**
     * Adds a zero-length null token to the end of the token list that is currently built.
     *
     * @param tokenizedChars
     *     the text of the entire document being tokenized.
     * @param offset
     *     the position of the null token in the document.
     * @param lineOffset
     *     the position of the null token relative to the start of the line on
     *     which it resides.
     */
    default void addNullToken(
        final char[] tokenizedChars,
        final int offset,
        final int lineOffset
    ) {
        this.addToken(tokenizedChars, offset, offset, RVTokenType.NULL, lineOffset);
    }

    /**
     * Adds a zero-length error token to the end of the token list that is currently built.
     * The error token may contain an additional error message.
     *
     * @param tokenizedChars
     * @param offset
     * @param lineOffset
     * @param errorMessage
     */
    default void addErrorToken(
        final char[] tokenizedChars,
        final int offset,
        final int lineOffset,
        final String errorMessage
    ) {
        this.addToken(tokenizedChars, offset, offset, RVTokenType.ERROR, lineOffset);
    }

    /**
     * Returns the result
     */
    CollectionType getResult();

    CollectionType getEmptyResult();

    CollectionType getTokenList(Segment text, int initialTokenType, int lineOffset, int lineNum);
}
