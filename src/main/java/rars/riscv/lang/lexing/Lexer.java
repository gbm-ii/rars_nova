package rars.riscv.lang.lexing;

import javax.swing.text.Segment;

/**
 * A lexer interface used for tokenizing lines of text.
 *
 * @param <TokenList>
 *     the type representing a collection of tokens in a line of text.
 * @param <Builder>
 *     a {@link TokenBuilder} implementation that is used for producing the token list.
 */
public interface Lexer<TokenList, Builder extends TokenBuilder<TokenList>> {
    /**
     * Produces and return a token list for the given line of text.
     *
     * @param text
     *     a {@link Segment} corresponding to the *entire* document being tokenized.
     * @param initialTokenType
     *     a token type corresponding to the last token on the previous line.
     * @param lineOffset
     *     the offset of the first character of the line in the document.
     * @param producer
     *     the {@link TokenBuilder} implementation to use for producing the token list.
     * @return a token list representing the tokens in the given line of text.
     */
    TokenList getTokensList(
        Segment text,
        int initialTokenType,
        int lineOffset,
        Builder producer
    );
}
