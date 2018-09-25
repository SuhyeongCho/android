package com.example.suhyeongcho.ssubs.userActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.suhyeongcho.ssubs.R;

import org.json.JSONArray;
import org.json.JSONObject;


public class Info extends AppCompatActivity {
    //private ArrayList<HashMap<String, String>> menuList;
    //private SimpleAdapter adapter;


    private TextView info_name;
    private TextView info_align;
    private TextView info_phone;
    private TextView info_time;
    private TextView info_address;

    //private ListView listViewMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Intent intent = getIntent();

        //menuList = new ArrayList<HashMap<String, String>>();

        info_name = findViewById(R.id.info_title);
        info_align = findViewById(R.id.info_align);
        info_phone = findViewById(R.id.info_phone);
        info_time = findViewById(R.id.info_time);
        info_address = findViewById(R.id.info_address);

        //listViewMenu = findViewById(R.id.info_menu_list);

        parse(intent.getStringExtra("information"));
        //String[] from = new String[] { "foodName", "price"};
        //int[] to = new int[] { R.id.menu_list_name, R.id.menu_list_price };
        //adapter = new SimpleAdapter(this, menuList, R.layout.food_list_layout, from, to);

        //listViewMenu.setAdapter(adapter);

        info_align.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ReservationActivity.class);
                intent.putExtra("R_name",info_name.getText().toString());
                startActivity(intent);
                /*
                move to reservation activity
                 */
            }
        });
    }

    private void parse(String data){
        try{
            JSONArray jarray = new JSONArray(data);
            JSONObject job = jarray.getJSONObject(0);
            String name = job.getString("name");
            String align = job.getString("align");
            String phone = job.getString("tel");
            String time = job.getString("open_t")+" - "+job.getString("close_t");
            String address = job.getString("address");

            info_name.setText(name);
            info_align.setText(align);
            info_phone.setText(phone);
            info_time.setText(time);
            info_address.setText(address);

//            JSONArray menu = job.getJSONArray("menu");
//            for(int i = 0 ; i < menu.length() ; i++){
//                JSONObject ob = menu.getJSONObject(i);
//                HashMap<String,String> map = new HashMap<>();
//                String menuName = ob.getString("name");
//                String menuPrice = ob.getString("price");
//                map.put("menuName",menuName);
//                map.put("menuPrice",menuPrice);
//                menuList.add(map);
//            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
