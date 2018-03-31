package top.huahaizhi.onlyu.activity;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.Random;

import top.huahaizhi.onlyu.R;
import top.huahaizhi.onlyu.bean.SettingsBean;
import top.huahaizhi.onlyu.database.SQLiteHelper;
import top.huahaizhi.onlyu.service.YiYanService;
import top.huahaizhi.onlyu.thread.BaseRequest;
import top.huahaizhi.onlyu.widget.MissView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener, TextWatcher {

    private Spinner spinnerMainSelectTime;
    private Spinner spinnerMainSelectType;
    private SwitchCompat toggleMainSaveYiYan;
    private AppCompatButton btMainShowLocalYiYan;
    private NumberPicker numPickerMainSelectNum;
    private TextView textMainDemo;
    private EditText etInputTextColor;
    private EditText etInputCustomYiyan;
    private Spinner spinnerMainSelectClickEvent;
    private Spinner spinnerMainSelectGravity;
    private SwitchCompat toggleMainTextFrom;
    private SwitchCompat toggleMainAddDot;
    private Button btMainDonate;
    private Button btMainAbout;

    private SettingsBean settingsBean;
    private SQLiteHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = new SQLiteHelper(this);
        if (AppWidgetManager.getInstance(this).getAppWidgetIds(new ComponentName(this, MissView.class)).length != 0)
            startService(new Intent(this, YiYanService.class).putExtra("Update", false));
        try {
            settingsBean = helper.getSettingsDao().queryForId(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initView();
    }

    private void initView() {
        spinnerMainSelectTime = (Spinner) findViewById(R.id.spinner_main_selectTime);
        spinnerMainSelectType = (Spinner) findViewById(R.id.spinner_main_selectType);
        toggleMainSaveYiYan = (SwitchCompat) findViewById(R.id.toggle_main_saveYiYan);
        btMainShowLocalYiYan = (AppCompatButton) findViewById(R.id.bt_main_showLocalYiYan);
        numPickerMainSelectNum = (NumberPicker) findViewById(R.id.numPicker_main_selectNum);
        textMainDemo = (TextView) findViewById(R.id.text_main_demo);
        etInputTextColor = (EditText) findViewById(R.id.et_main_inputColor);
        etInputCustomYiyan = (EditText) findViewById(R.id.et_main_inputYiYan);
        spinnerMainSelectClickEvent = (Spinner) findViewById(R.id.spinner_main_selectClickEvent);
        spinnerMainSelectGravity = (Spinner) findViewById(R.id.spinner_main_selectGravity);
        toggleMainTextFrom = (SwitchCompat) findViewById(R.id.toggle_main_textFrom);
        toggleMainAddDot = (SwitchCompat) findViewById(R.id.toggle_main_addDot);
        btMainDonate = (Button) findViewById(R.id.bt_main_donate);
        btMainAbout = (Button) findViewById(R.id.bt_main_about);

        numPickerMainSelectNum.setMinValue(9);
        numPickerMainSelectNum.setMaxValue(30);

        btMainShowLocalYiYan.setOnClickListener(this);
        btMainDonate.setOnClickListener(this);
        btMainAbout.setOnClickListener(this);

        etInputCustomYiyan.addTextChangedListener(this);

        spinnerMainSelectTime.setAdapter(getAdapter(R.array.selectRequestLoop));
        spinnerMainSelectType.setAdapter(getAdapter(R.array.selectRequestType));
        spinnerMainSelectClickEvent.setAdapter(getAdapter(R.array.clickEvents));
        spinnerMainSelectGravity.setAdapter(getAdapter(R.array.textGravity));

        spinnerMainSelectType.setOnItemSelectedListener(this);
        spinnerMainSelectType.setOnItemSelectedListener(this);
        spinnerMainSelectClickEvent.setOnItemSelectedListener(this);
        spinnerMainSelectGravity.setOnItemSelectedListener(this);

        toggleMainSaveYiYan.setOnCheckedChangeListener(this);
        toggleMainTextFrom.setOnCheckedChangeListener(this);
        toggleMainAddDot.setOnCheckedChangeListener(this);

        numPickerMainSelectNum.setWrapSelectorWheel(false);
        numPickerMainSelectNum.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                textMainDemo.setTextSize(newVal);
                settingsBean.setTextSize(newVal);
                update();
            }
        });
        etInputTextColor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 6) {
                    try {
                        int color = Color.parseColor("#" + s.toString());
                        settingsBean.setTextColor(color);
                        Toast.makeText(MainActivity.this, "更新颜色成功~（#" + s.toString() + "）", Toast.LENGTH_SHORT).show();
                        update();
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "啊哦……颜色值好像不对哦~", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        if (settingsBean != null) {
            spinnerMainSelectTime.setSelection(settingsBean.getRequestTime());
            spinnerMainSelectType.setSelection(settingsBean.getRequestType());
            toggleMainSaveYiYan.setChecked(settingsBean.isSaveYiYanToLocal());
            numPickerMainSelectNum.setValue(settingsBean.getTextSize());
            textMainDemo.setTextSize(settingsBean.getTextSize());
            spinnerMainSelectClickEvent.setSelection(settingsBean.getClickEvent());
            spinnerMainSelectGravity.setSelection(settingsBean.getTextGravity());

            toggleMainTextFrom.setChecked(settingsBean.isTextFrom());
            toggleMainAddDot.setChecked(settingsBean.isAddTextDot());

            etInputCustomYiyan.setText(settingsBean.getCustomYiYan());
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_main_showLocalYiYan:
                startActivity(new Intent(this, YiYanHistoryActivity.class));
                break;
            case R.id.bt_main_donate:
                new AlertDialog.Builder(this).setTitle("非常感谢:)").setMessage("非常感谢你的心意，你无需为此捐赠。这是一个开源项目。如果你的手头非常宽裕，可以考虑为OpenSSL™捐款").setPositiveButton("转到OpenSSL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gotoWeb("https://www.openssl.org/");
                    }
                }).setNeutralButton("项目地址(GitHub)", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gotoWeb("https://github.com/HaizhiH/hongdou/");
                    }
                }).setNegativeButton("取消", null).show();
                break;
            case R.id.bt_main_about:
                if (new Random().nextInt(2) + 1 == 1)
                    Toast.makeText(this, "❤", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "✨", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private ArrayAdapter<String> getAdapter(int arrayId) {
        return new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(arrayId));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinner_main_selectType:
                settingsBean.setRequestType(position);
                break;
            case R.id.spinner_main_selectTime:
                settingsBean.setRequestTime(position);
                break;
            case R.id.spinner_main_selectGravity:
                settingsBean.setTextGravity(position);
                break;
            case R.id.spinner_main_selectClickEvent:
                settingsBean.setClickEvent(position);
                break;
        }
        update();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.toggle_main_addDot:
                settingsBean.setAddTextDot(isChecked);
                break;
            case R.id.toggle_main_saveYiYan:
                settingsBean.setSaveYiYanToLocal(isChecked);
                break;
            case R.id.toggle_main_textFrom:
                settingsBean.setTextFrom(isChecked);
                break;
        }
        update();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String customYiYan = editable.toString();
        if (customYiYan.hashCode() == etInputCustomYiyan.getText().toString().hashCode()) {
            settingsBean.setCustomYiYan(customYiYan);
        }
    }

    private void gotoWeb(String url) {
        if (TextUtils.isEmpty(url))
            return;
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private void update() {
        try {
            helper.getSettingsDao().update(settingsBean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}