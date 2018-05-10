package kr.ac.kumoh.s20110214.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity  {

    private static final String FCM_MESSAGE_URL = "https://fcm.googleapis.com/fcm/send";        //
    //파이어베이스에서 제공하는 서버키
    private static final String SERVER_KEY = "AAAAqI37PrY:APA91bF2BK5FkCSH-ovJCaeWmk1PMvBUDb-9S5QivLSo9Y3yhaTRhh3-0LCgmcIIeDNSLtbHlGEow7s9b4atpDfzqrH0yeOSKOPZd5vyCLf71AhCmt8d6iTG8QYmVO8vjOwUTFqV5mJI";

     Button btn=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fcm);

        btn=findViewById(R.id.btn_send2);

        //제작한 클래스
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // FMC 메시지 생성 start
                            JSONObject root = new JSONObject();                              //root 제이슨 생성
                            JSONObject notification = new JSONObject();                      //알림 제이슨 생성
                            notification.put("body", "제발 되라");           //에딧 텍스트값이 들어옴
                            notification.put("title", "안녕");//앱이름 얻어옴 (타이틀에)
                            root.put("notification", notification);                 //제이슨 안에 제이슨넣음
                            //토큰값 넣음
                            root.put("to", "fWBhZ24sIng:APA91bGh6-9xTg4W57UDQJMfcoJpZHnsulHgJj034P6s1E63vJ5f5VZNuB0ZdPFKWgeIZMFEBbIyNkL2jwEHOe2ir2_wDYq_eU7IMAT8GVuoMngN3pgWP40qC7D6D2gc0TlulEJZ75x7");                      //토큰값넣음
                            // FMC 메시지 생성 end

                            URL Url = new URL(FCM_MESSAGE_URL);                          //url 인스턴스 생성
                            HttpURLConnection conn = (HttpURLConnection) Url.openConnection();// 해당 주소의 페이지로 접속을 하고, 단일 HTTP 접속을 하기위해 캐스트한다.
                            conn.setRequestMethod("POST");                                  // POST방식으로 요청한다.( 기본값은 GET )
                            conn.setDoOutput(true);                                         // OutputStream으로 POST 데이터를 넘겨주겠다는 옵션을 정의한다.
                            conn.setDoInput(true);                                          // inputStream으로 응답 헤더와 메시지를 읽어들이겠다는 옵션을 정의한다
                            conn.addRequestProperty("Authorization", "key=" + SERVER_KEY); //http 요청 헤더를 설정
                            conn.setRequestProperty("Accept", "application/json");      //request header josn형식 값 세팅
                            conn.setRequestProperty("Content-type", "application/json");
                            OutputStream os = conn.getOutputStream();                       //request body에 data 닮기 위해 outputstream 객체 생성
                            os.write(root.toString().getBytes("utf-8"));     //request body에 data 셋팅
                            os.flush();                                                     //request body에 data 입력
                            conn.getResponseCode();                                         //실제 서버에 request 요청
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

    }

}

