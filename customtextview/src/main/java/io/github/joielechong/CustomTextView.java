package io.github.joielechong;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.TextAppearanceSpan;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Joielechong on 1/27/16.
 */
public class CustomTextView extends TextView {

  private String prefix;
  private String suffix;
  private int prefixStyle;

  public CustomTextView(Context context) {
    super(context);
    init(context, null, 0, 0);
  }

  public CustomTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs, 0, 0);
  }

  public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs, defStyleAttr, 0);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(context, attrs, defStyleAttr, defStyleRes);
  }

  protected void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    if (attrs == null) {
      this.prefix = "";
      this.suffix = "";
      return;
    }

    final TypedArray attributes =
        context.obtainStyledAttributes(attrs, R.styleable.CustomTextView, defStyleAttr,
            defStyleRes);

    try {
      final CharSequence prefix = attributes.getText(R.styleable.CustomTextView_ct_prefix);
      if (prefix != null) {
        this.prefix = prefix.toString();
      } else {
        this.prefix = "";
      }

      final CharSequence suffix = attributes.getText(R.styleable.CustomTextView_ct_suffix);
      if (suffix != null) {
        this.suffix = suffix.toString();
      } else {
        this.suffix = "";
      }

      this.prefixStyle = attributes.getInteger(R.styleable.CustomTextView_ct_prefix_style, 0);

    } finally {
      attributes.recycle();
    }

    updateText();
  }

  private void updateText() {
    if(prefixStyle == 0) {
      setText(prefix + this.getText() + suffix);
    } else {
      SpannableStringBuilder builder = new SpannableStringBuilder()
          .append(applyStyle())
          .append(this.getText())
          .append(suffix);
      CharSequence styledString = builder.subSequence(0, builder.length());

      setText(styledString);
    }
  }


  private SpannableString applyStyle() {

    SpannableString spannableString = new SpannableString(prefix);

    if((prefixStyle & PrefixStyle.BOLD) == PrefixStyle.BOLD) {
      spannableString.setSpan(new TextAppearanceSpan(getContext(), R.style.Bold), 0, prefix.length(), 0);
    }
    if((prefixStyle & PrefixStyle.ITALIC) == PrefixStyle.ITALIC) {
      spannableString.setSpan(new TextAppearanceSpan(getContext(), R.style.Italic), 0, prefix.length(), 0);
    }
    if((prefixStyle & PrefixStyle.LARGE) == PrefixStyle.LARGE) {
      spannableString.setSpan(new TextAppearanceSpan(getContext(), R.style.Large), 0, prefix.length(), 0);
    }

    return spannableString;
  }

  public String getPrefix() {
    return prefix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public String getSuffix() {
    return suffix;
  }

  public void setSuffix(String suffix) {
    this.suffix = suffix;
  }

  public int getPrefixStyle() {
    return prefixStyle;
  }

  public void setPrefixStyle(int prefixStyle) {
    this.prefixStyle = prefixStyle;
  }
}
