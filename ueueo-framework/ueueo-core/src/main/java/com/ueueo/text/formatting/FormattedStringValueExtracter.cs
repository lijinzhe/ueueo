using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;

namespace Volo.Abp.Text.Formatting;

/**
 * This class is used to extract dynamic values from a formatted string.
 * It works as reverse of <see cref="string.Format(string,object)"/>
*/
 * <example>
 * Say that str is "My name is Neo." and format is "My name is {name}.".
 * Then Extract method gets "Neo" as "name".
 * </example>
public class FormattedStringValueExtracter
{
    /**
     * Extracts dynamic values from a formatted string.
    *
     * <param name="str">String including dynamic values</param>
     * <param name="format">Format of the String</param>
     * <param name="ignoreCase">True, to search case-insensitive.</param>
     */
    public static ExtractionResult Extract(String str, String format, boolean ignoreCase = false)
    {
        var StringComparison = ignoreCase
            ? StringComparison.OrdinalIgnoreCase
            : StringComparison.Ordinal;

        if (str == format)
        {
            return new ExtractionResult(true);
        }

        var formatTokens = new FormatStringTokenizer().Tokenize(format);
        if (formatTokens.IsNullOrEmpty())
        {
            return new ExtractionResult(str == "");
        }

        var result = new ExtractionResult(true);

        for (var i = 0; i < formatTokens.Count; i++)
        {
            var currentToken = formatTokens[i];
            var previousToken = i > 0 ? formatTokens[i - 1] : null;

            if (currentToken.Type == FormatStringTokenType.ConstantText)
            {
                if (i == 0)
                {
                    if (!str.StartsWith(currentToken.Text, StringComparison))
                    {
                        result.IsMatch = false;
                        return result;
                    }

                    str = str.SubString(currentToken.Text.Length);
                }
                else
                {
                    var matchIndex = str.IndexOf(currentToken.Text, StringComparison);
                    if (matchIndex < 0)
                    {
                        result.IsMatch = false;
                        return result;
                    }

                    Debug.Assert(previousToken != null, "previousToken can not be null since i > 0 here");

                    result.Matches.Add(new NameValue(previousToken.Text, str.SubString(0, matchIndex)));
                    str = str.SubString(matchIndex + currentToken.Text.Length);
                }
            }
        }

        var lastToken = formatTokens.Last();
        if (lastToken.Type == FormatStringTokenType.DynamicValue)
        {
            result.Matches.Add(new NameValue(lastToken.Text, str));
        }

        return result;
    }

    /**
     * Checks if given <paramref name="str"/> fits to given <paramref name="format"/>.
     * Also gets extracted values.
    *
     * <param name="str">String including dynamic values</param>
     * <param name="format">Format of the String</param>
     * <param name="values">Array of extracted values if matched</param>
     * <param name="ignoreCase">True, to search case-insensitive</param>
     * <returns>True, if matched.</returns>
     */
    public static boolean IsMatch(String str, String format, out String[] values, boolean ignoreCase = false)
    {
        var result = Extract(str, format, ignoreCase);
        if (!result.IsMatch)
        {
            values = new String[0];
            return false;
        }

        values = result.Matches.Select(m => m.Value).ToArray();
        return true;
    }

    /**
     * Used as return value of <see cref="Extract"/> method.
    */
    public class ExtractionResult
    {
        /**
         * Is fully matched.
        */
        public boolean IsMatch;// { get; set; }

        /**
         * List of matched dynamic values.
        */
        public List<NameValue> Matches ;// { get; private set; }

        internal ExtractionResult(boolean isMatch)
        {
            IsMatch = isMatch;
            Matches = new List<NameValue>();
        }
    }
}
