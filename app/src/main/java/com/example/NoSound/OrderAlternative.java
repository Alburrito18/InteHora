package com.example.NoSound;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.NoSound.BusinessView.BusinessData;
import com.example.NoSound.BusinessView.BusinessView;

import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileOutputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderAlternative#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderAlternative extends Fragment {

    private BusinessData businessData;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OrderAlternative() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderAlternativ.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderAlternative newInstance(String param1, String param2) {
        OrderAlternative fragment = new OrderAlternative();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_alternativ, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int latestOrderID = ((MainActivity) requireActivity()).getLatestOrderID();
        businessData = ((MainActivity) requireActivity()).getBusinessData(latestOrderID);
        ((TextView)view.findViewById(R.id.companyName)).setText(businessData.getCustomerName());
        ((TextView)view.findViewById(R.id.date)).setText(businessData.getDate());
        ((TextView)view.findViewById(R.id.orderNum)).setText("Ordernr: " + businessData.getOrderID());
        view.findViewById(R.id.orderButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(OrderAlternative.this)
                        .navigate(R.id.action_orderAlternative_to_edit_order_page);
            }
        });
        view.findViewById(R.id.wordButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDocx(businessData);
            }
        });

        }

    private void createDocx(BusinessData businessData){

        ((MainActivity) requireActivity()).generateDocx();
        try {
            XWPFDocument xwpfDocument = new XWPFDocument();
            XWPFParagraph xwpfParagraph = xwpfDocument.createParagraph();
            XWPFRun xwpfRun = xwpfParagraph.createRun();
            Log.d("BusineddDataToString",businessData.toString());
            String data = businessData.toString();
            if (data.contains("\n")) {
                String[] lines = data.split("\n");
                xwpfRun.setText(lines[0], 0); // set first line into XWPFRun
                for(int i=1;i<lines.length;i++){
                    // add break and insert new text
                    xwpfRun.addBreak();
                    xwpfRun.setText(lines[i]);
                }
            } else {
                xwpfRun.setText(data, 0);
            }
            xwpfRun.setFontSize(12);
            FileOutputStream fileOutputStream = new FileOutputStream(((MainActivity) requireActivity()).fileGetter());
            xwpfDocument.write(fileOutputStream);

            if (fileOutputStream != null){
                fileOutputStream.flush();
                fileOutputStream.close();
            }

            xwpfDocument.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}