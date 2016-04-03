package com.nn.shopfinder.activity.dialog;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nn.shopfinder.R;

/**
 * Created by Nuno on 03/04/2016.
 */
public class GenericDialog extends DialogFragment {

    private static final String TAG = "DIALOG_GENERIC";
    private Button btn1, btn2;
    private TextView textView;

    //Messages
    private String textViewStr, btn1Str, btn2Str;

    //Behaviour
    private Boolean closeOnAction, closeOnOutsideAction;
    private GenericDialogCallback callback;


    public static GenericDialog newInstance(String msg){
        GenericDialog d = new GenericDialog();
        Bundle args = new Bundle();
        args.putString("msg", msg);
        d.setArguments(args);
        return d;
    }

    public static GenericDialog newInstance(String msg, String btn1, String btn2){
        GenericDialog d = new GenericDialog();
        Bundle args = new Bundle();
        args.putString("msg", msg);
        args.putString("btn1", btn1);
        args.putString("btn2", btn2);
        d.setArguments(args);
        return d;
    }

    public static GenericDialog newInstance(String msg, String btn1, String btn2,Boolean closeOnAction, Boolean closeOnOutsideAction){
        GenericDialog d = new GenericDialog();
        Bundle args = new Bundle();
        args.putString("msg", msg);
        args.putString("btn1", btn1);
        args.putString("btn2", btn2);
        args.putBoolean("closeOnAction",closeOnAction);
        args.putBoolean("closeOnOutsideAction",closeOnOutsideAction);
        d.setArguments(args);
        return d;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){
            textViewStr = savedInstanceState.getString("msg");
            btn1Str = savedInstanceState.getString("btn1");
            btn2Str = savedInstanceState.getString("btn2");
            closeOnAction = savedInstanceState.getBoolean("closeOnAction");
            closeOnOutsideAction = savedInstanceState.getBoolean("closeOnOutsideAction");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        getDialog().setCanceledOnTouchOutside(closeOnOutsideAction != null ? closeOnOutsideAction : false);
        onDismiss(new DialogInterface() {
            @Override
            public void cancel() {

            }

            @Override
            public void dismiss() {
                callback.onDismiss();
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_generic,container);

        getDialog().setTitle("Alerta");

        btn1 = (Button)view.findViewById(R.id.btn1);
        btn2 = (Button)view.findViewById(R.id.btn2);
        textView = (TextView)view.findViewById(R.id.textView);

        btn1.setText(btn1Str != null? btn1Str : getResources().getString(R.string.dialog_generic_btn1));
        btn2.setText(btn2Str != null? btn2Str : getResources().getString(R.string.dialog_generic_btn2));
        textView.setText(textViewStr != null? textViewStr : "");

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onButton1Pressed();
                if (closeOnAction != null && closeOnOutsideAction)
                    dismiss();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onButton2Pressed();
                if (closeOnAction != null && closeOnOutsideAction)
                    dismiss();
            }
        });

        return view;
    }

    public interface GenericDialogCallback{

        void onButton1Pressed();
        void onButton2Pressed();
        void onDismiss();


    }
}
