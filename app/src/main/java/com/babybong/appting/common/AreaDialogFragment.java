package com.babybong.appting.common;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.babybong.appting.R;

/**
 * Created by hoon on 2015-08-10.
 */
public class AreaDialogFragment extends DialogFragment {
    private ListView mListView1;
    private ListView mListView2;
    private ArrayAdapter<String> mAdapter1;
    private ArrayAdapter<String> mAdapter2;

    private String area1, area2;

    public AreaDialogFragment() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_area_dialog, null);

        mAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_checked);
        mAdapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_checked);

        mListView1 = (ListView)view.findViewById(R.id.listview1);
        mListView1.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mListView1.setAdapter(mAdapter1);

        // ListView에 지역1 아이템 추가
        final String items[] = getResources().getStringArray(R.array.area1);
        for (String area : items) {
            mAdapter1.add(area);
        }
        // ListView 아이템 터치 시 이벤트 추가
        mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // parent는 AdapterView의 속성의 모두 사용 할 수 있다.
                String strArea1 = (String) parent.getAdapter().getItem(position);
                area1 = strArea1;
                Toast.makeText(getActivity(), strArea1 + ":" + position, Toast.LENGTH_SHORT).show();
                Log.d("hoon", position + ">> selected >> " + mListView1.getItemAtPosition(position).toString());

               if ("서울".equals(mListView1.getItemAtPosition(position).toString())) {
                    mAdapter2.clear();
                    final String items[] = getResources().getStringArray(R.array.seoul);
                    for (String area : items) {
                        mAdapter2.add(area);
                    }
                } else if ("경기".equals(mListView1.getItemAtPosition(position).toString())) {
                    mAdapter2.clear();
                    final String items[] = getResources().getStringArray(R.array.kunggi);
                    for (String area : items) {
                        mAdapter2.add(area);
                    }
                } else {
                    mAdapter2.clear();
                }

            }
        });

        mListView2 = (ListView)view.findViewById(R.id.listview2);
        mListView2.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mListView2.setAdapter(mAdapter2);
        mListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                area2 = mListView2.getItemAtPosition(position).toString();
                Log.d("hoon", ">>2 selected>> " + area2);
            }
        });

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view).setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        AreaDialogInterface areaDialogInterface = (AreaDialogInterface)getActivity();
                        areaDialogInterface.selectedArea(area1, area2);
                    }
                }).setNegativeButton("취소", null);
        return builder.create();
    }
}
