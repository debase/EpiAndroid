package epiandroid.eu.epitech.epiandroid.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import epiandroid.eu.epitech.epiandroid.R;
import epiandroid.eu.epitech.epiandroid.activity.HomeActivity;
import epiandroid.eu.epitech.epiandroid.adapter.PlanningAdapter;
import epiandroid.eu.epitech.epiandroid.epitech_service.EpitechService;
import epiandroid.eu.epitech.epiandroid.epitech_service.GsonResponseHandler;
import epiandroid.eu.epitech.epiandroid.model.PlanningItem;
import epiandroid.eu.epitech.epiandroid.utils.Utils;

public class PlanningFragment extends LoadingFragment implements View.OnClickListener {

    private ListView planningList;
    private TextView dateLabel;
    private TextView noEventLabel;
    private Calendar dateCal = Calendar.getInstance();
    private PlanningAdapter listAdapter;
    private Date currentDate = new Date();
    private String[] monthsNb = new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    private String[] months;
    private Activity mActivity = null;
    private EditText input;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    private String mOcrPath = HomeActivity.DATA_PATH + "/ocr.jpg";
    public static String TAG = "PlanningFragment";
    private TessBaseAPI mBaseApi = new TessBaseAPI();


    private JsonHttpResponseHandler requestHandler = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);

            if (mActivity == null)
                return;

            try {
                response.getString("error");
                Utils.makeText(mActivity, getResources().getString(R.string.token_error));
            } catch (JSONException e) {
                Utils.makeText(mActivity, getResources().getString(R.string.token_success));
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
            if (mActivity == null)
                return;

            Log.i("Token Validation Error", responseString);
        }
    };

    private GsonResponseHandler<PlanningItem[]> gsonResponseHandler = new GsonResponseHandler<PlanningItem[]>(PlanningItem[].class) {
        @Override
        public void onSuccess(PlanningItem[] planningItems) {
            if (mActivity == null)
                return;

            ArrayList<PlanningItem> contentList = new ArrayList<>();
            listAdapter = new PlanningAdapter(mActivity, PlanningFragment.this, R.layout.adapter_planning, contentList);

            showLoading(false, null);
            showbaseView(true);
            if (planningItems != null) {
                for (PlanningItem planningItem : planningItems) {
                    if (planningItem.getRegistered() != null && planningItem.getRegistered().equals("registered")) {
                        listAdapter.add(planningItem);
                    }
                }
                if (listAdapter.getCount() == 0) {
                    noEventLabel.setVisibility(View.VISIBLE);
                }
                planningList.setAdapter(listAdapter);
            } else {
                noEventLabel.setVisibility(View.VISIBLE);
                listAdapter.clear();
                planningList.setAdapter(listAdapter);
            }
        }

        @Override
        public void onFailure(Throwable throwable, JSONObject errorResponse) {
            if (mActivity == null)
                return;
            showLoading(false, null);
            showError(true, getResources().getString(R.string.error_fetching_data));
        }
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    public PlanningFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        months = new String[] {getResources().getString(R.string.january),
                getResources().getString(R.string.february),
                getResources().getString(R.string.march),
                getResources().getString(R.string.april),
                getResources().getString(R.string.may),
                getResources().getString(R.string.june),
                getResources().getString(R.string.july),
                getResources().getString(R.string.august),
                getResources().getString(R.string.september),
                getResources().getString(R.string.october),
                getResources().getString(R.string.november),
                getResources().getString(R.string.december)};
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_planning, container, false);
        this.planningList = (ListView) v.findViewById(R.id.planningList);
        this.dateLabel = (TextView) v.findViewById(R.id.planningDate);
        this.noEventLabel = (TextView) v.findViewById(R.id.planningNoEvent);
        this.noEventLabel.setVisibility(View.GONE);
        ImageButton prevBtn = (ImageButton) v.findViewById(R.id.btnPrevDay);
        ImageButton nextBtn = (ImageButton) v.findViewById(R.id.btnNextDay);
        prevBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        dateCal.setTime(currentDate);
        this.dateLabel.setText(dateCal.get(Calendar.DAY_OF_MONTH) + " " + months[dateCal.get(Calendar.MONTH)] + " " + dateCal.get(Calendar.YEAR));

        setLoadingView(planningList, v.findViewById(R.id.layout_spinner_loading), v.findViewById(R.id.layout_spinner_error));
        v.findViewById(R.id.button_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showError(false, null);
                load();
            }
        });
        load();
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.planningList = null;
        this.dateLabel = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNextDay :
                dateCal.add(Calendar.DATE, 1);
                load();
                break;
            case R.id.btnPrevDay :
                dateCal.add(Calendar.DATE, -1);
                load();
                break;
            case R.id.btnToken :
                Utils.makeText(mActivity, "position : " + v.getTag());
                validateToken(v);
                break;
        }
    }

    private void validateToken(View v) {
        int position = (int) v.getTag();

        LayoutInflater layoutInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View tokenValidationView = layoutInflater.from(mActivity).inflate(R.layout.token_validation, null);

        input = (EditText) tokenValidationView.findViewById(R.id.token_input);
        ImageButton cameraInput = (ImageButton) tokenValidationView.findViewById(R.id.camera_image);

        cameraInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager packageManager = mActivity.getPackageManager();
                if (!packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                    Toast.makeText(getActivity(), "This device does not have a camera.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = new File(mOcrPath);
                Uri outputFileUri = Uri.fromFile(file);

//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                startActivityForResult(takePictureIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });

        final PlanningItem tokenPlanningItem = listAdapter.getItem(position);
        new AlertDialog.Builder(mActivity)
                .setTitle(getResources().getString(R.string.enter_token))
                .setView(tokenValidationView)
                .setPositiveButton(getResources().getString(R.string.validate), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String token = input.getText().toString();

                        RequestParams params = new RequestParams();
                        params.put("scolaryear", tokenPlanningItem.getScolaryear());
                        params.put("codemodule", tokenPlanningItem.getCodemodule());
                        params.put("codeinstance", tokenPlanningItem.getCodeinstance());
                        params.put("codeacti", tokenPlanningItem.getCodeacti());
                        params.put("codeevent", tokenPlanningItem.getCodeevent());
                        params.put("tokenvalidationcode", token);

                        EpitechService.postRequest("token", params, requestHandler);
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Do nothing.
                    }
                }).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Bitmap bitmap = data.getExtras().getParcelable("data");

            mBaseApi.init(HomeActivity.DATA_PATH, HomeActivity.lang);
            mBaseApi.setImage(toGrayscale(bitmap));

            final ProgressDialog progress = ProgressDialog.show(mActivity, getResources().getString(R.string.dialog_ocr_title),
                    getResources().getString(R.string.dialog_ocr_message), true);

            new AsyncTask<TessBaseAPI, Void, String>() {

                @Override
                protected void onPreExecute() {
                    progress.show();
                }

                @Override
                protected String doInBackground(TessBaseAPI... params) {
                    return params[0].getUTF8Text();
                }

                @Override
                protected void onPostExecute(String result) {
                    result = result.replaceAll("\\D+","");
                    input.setText(result);
                    progress.dismiss();
                }
            }.execute(mBaseApi);

        }
    }

    public static Bitmap toGrayscale(Bitmap bmpOriginal)
    {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }

    @Override
    public void load() {
        showbaseView(false);
        showLoading(true, getResources().getString(R.string.loading_data));
        this.dateLabel.setText(dateCal.get(Calendar.DAY_OF_MONTH) + " " + months[dateCal.get(Calendar.MONTH)] + " " + dateCal.get(Calendar.YEAR));
        RequestParams params = new RequestParams();
        String dateStr = dateCal.get(Calendar.YEAR) + "-" + monthsNb[dateCal.get(Calendar.MONTH)] + "-" + dateCal.get(Calendar.DAY_OF_MONTH);
        params.put("start", dateStr);
        params.put("end", dateStr);
        if (listAdapter != null) {
            listAdapter.clear();
            planningList.setAdapter(listAdapter);
        }
        this.noEventLabel.setVisibility(View.GONE);
        EpitechService.getRequest("planning", params, gsonResponseHandler);
    }
}
