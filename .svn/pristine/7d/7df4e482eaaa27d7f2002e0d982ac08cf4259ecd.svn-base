package com.lion.apiDispose;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lion.R;

/**
 * 提交数据统一对画框
 * 
 * @author zhangbp
 * 
 */
public class LionProgRessDialog extends Dialog {
	private TextView toask_txt;
	private String msg;

	public LionProgRessDialog(Context context, String msg) {
		this(context, 0, msg);
		// TODO Auto-generated constructor stub
	}

	public LionProgRessDialog(Context context) {
		// TODO Auto-generated constructor stub
		this(context, 0, null);
	}

	public LionProgRessDialog(Context context, int theme) {
		// TODO Auto-generated constructor stub
		this(context, theme, null);
	}

	public LionProgRessDialog(Context context, int theme, String msg) {
		super(context, theme == 0 ? R.style.lion_dialog_transparent : theme);
		// TODO Auto-generated constructor stub
		this.msg = msg;
		setCancelable(false);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.market_progress_dialog);
		toask_txt = (TextView) findViewById(R.id.toask_txt);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();

		if (msg != null && toask_txt != null) {
			toask_txt.setText(msg);
		}
	}

	public void hideTxt() {
		if (toask_txt != null) {
			toask_txt.setVisibility(View.GONE);
		}
	}

	public void setMsg(String msg) {
		if (toask_txt != null  && toask_txt != null) {
			toask_txt.setText(msg);
		}
	}

}
