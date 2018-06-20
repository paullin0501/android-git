package com.example.asus.masteryviiintaichung;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Place> places = new ArrayList<Place>();
    SQLiteDatabase db;
    DBOpenHelper openHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openHelper = new DBOpenHelper(this);
        db = openHelper.getWritableDatabase();
        db.execSQL("insert into me values('My name', 1.0)");
        db.close();

        PlaceArrayAdapter adapter = new PlaceArrayAdapter(this, places);

        places.add(new Place("勤美術館", R.drawable.artist, "台中市西區館前路71號",
                0.5f, 24.152590, 120.663630,
                "勤美術館以活的、無疆界的美術館為定位，由勤美集團與璞真建設聯手，推出佔地2,500坪的「勤美術館CMP Block Museum of Arts」。勤美術館以草悟道沿線為範圍，號召台灣各界藝術家及特色店家參與，讓民眾體會在地的藝術街區。\n\n" +
                      "四周聚集了許多特色小店，不僅逐漸形成藝術家聚落，更創造了動靜態共存的美學形式。勤美術館致力於成為兼具國際高度與在地深度的藝術街區，讓藝術滲透民眾的日常生活。\n\n" +
                      "勤美術館也是臺灣第一座有機的無牆美術館。以無疆界街區美術館為定位，主打生活化的親民藝術。除了舊校舍改造的室內展演空間外，戶外則以「手打村落」的概念，分為「日晷牧場」、「串門子村落」、「大客廳」三大創意區塊。"));
        places.add(new Place("路思義教堂", R.drawable.church , "台中市西屯區台灣大道四段1727號",
                1.0f, 24.179081, 120.600822,
                "路思義教堂(The Luce Chapel) 是一座位於台灣台中市西屯區東海大學的基督新教禮拜堂，為著名台灣建築師陳其寬與美國華裔建築師貝聿銘之作。始建於1962年9月，並於1963年11月2日落成；2017年9月26日臺中市文化資產處以「路思義教堂及鐘樓」將其與畢律斯鐘樓登錄為臺中市定古蹟，衛理會館、舊藝術中心登錄為歷史建築。\n\n" +
                      "位於整個校園的中心，西接教學區，東接宿舍區，東海大學早期樹木尚未繁密之前，校園內任何一處地方都可看到路思義教堂，正因如此，它變成為東海人最重要的精神指標，為著名華裔美國建築師貝聿銘先生的傑作。\n\n" +
                      "教堂為了採光及明確表現結構起見，以四片三度曲面板構成，類似倒置船底，其上小下大的形狀給人一種穩定的感覺，從正前方或四十五度角內觀看，最能表現出路思義教堂那「曲線型」的美。每年耶誕節，路思義教堂是慶祝活動的重要地點。"));
        places.add(new Place("都會公園", R.drawable.citypark, "台中市西屯區都會園路1215巷140號",
                0.5f, 24.207877, 120.598880,
                "臺中都會公園位於臺中市大肚山台地，面積88公頃，於89年10月28日正式開放提供國人休憩活動使用，為繼高雄都會公園之後完成的第2座都會區大型綠地公園。園區地處大肚山台地稜線上，面積廣闊，又極接近台地的最高點（海拔310公尺），視野開闊，景觀壯麗。\n\n" +
                      "往東側眺望，可俯瞰臺中盆地，整個臺中市的美麗景緻盡收眼底。西側有著和東側截然不同的景觀，遠處的臺灣海峽，船隻往來清楚可見，臺中火力發電廠、臺中港盡在腳下。\n\n" +
                      "臺中都會公園的設立負起維護大肚山台地特殊自然景觀與環境的重要任務，同時也提供大臺中都會地區居民一個就近認識大自然及休閒遊憩的好去處，並提供野生動、植物一處棲息環境，肩負維護生物多樣性的重要功能。"));
        places.add(new Place("新社花海", R.drawable.flowersea,"台中市新社區協興街與興義街口",
                0.5f, 24.203127, 120.812316,
                "新社花海是台灣中部地區一座花卉景點區，位於台中市新社區境內，為農委會種苗改良繁殖場經營第二苗圃。苗圃設立於1915年，是屬於日治時期遺留的靜態產物，原初設立大南莊蔗苗養成所乃種植蔗苗，以試驗並改良蔗苗加以繁殖之用。\n\n" +
                      "每當秋冬之際，台中最為期待的盛事之一，莫過於在新社舉辦的「新社花海」，寬廣的園區種植了大量的花卉，置身其中猶如徜徉花海之中，就讓我們一起瘋花海，遊走屯區一日遊吧！\n\n" +
                      "一日之計在於晨；一生之計在於勤，最推薦的賞花時段莫過於是早晨的時段了，因此第一站直奔新社花海就對了！不僅僅免去塞車尖峰時段，早晨乾淨的光線更適合拍攝，還可以一邊呼吸著新社早晨新鮮的空氣，舒心快活許多。"));
        places.add(new Place("逢甲夜市", R.drawable.nightmarket,"台中市西屯區文華路",
                0.5f, 24.180033, 120.646365,
                "逢甲夜市位於台中市西屯區以文華路、福星路、逢甲路為主，是全台灣最大且著名的觀光夜市，逢甲夜市的消費以『俗擱大碗』為最吸引各路愛好夜市的民眾，若遇假日其人潮更是讓逢甲夜市主幹道塞得水泄不通；逢甲夜市以新奇又有趣的小吃聞名，成為創新小吃的原始地，例如逢甲四合一、蜂蜜檸檬蘆薈、可麗餅、胡椒餅等等，都是由此逢甲夜市發源再外傳到各地的哦。\n\n" +
                      "逢甲夜市讓您從夜市頭吃到夜市尾，美味小吃相當多樣，每個攤子都吃保證讓你有滿足感又不會讓你吃不飽，花少少錢吃到飽；『日船章魚小丸子』從逢甲夜市發跡，到今日全台灣已有多家分店，以平價且真材實料的日式小吃打動當地人的心；『起士洋芋』以進口的美國馬鈴薯搭配豐富的配料和濃濃的起士，熱騰騰的美味可要在逢甲夜市才吃得到"));
        places.add(new Place("台中歌劇院", R.drawable.opera,"台中市西屯區惠來路二段101號",
                1.0f, 24.163229, 120.640318,
                "。位於臺中市西屯區七期重劃區，是一大型公有展演空間，由曾獲普立茲克獎的日本建築師伊東豊雄設計。臺中國家歌劇院得天獨厚，在這完美建築之內，有三座專業劇場以及一個多功能空間「角落沙龍」；如果再加上藍天白雲下的廣場、戶外劇場、與屋頂的空中花園，歌劇院可說從裡到外、從地面到屋頂，都是魅力的舞台。\n\n" +
                      "臺中國家歌劇院造型前衛，以「美聲涵洞」概念，採用曲牆、孔洞與管狀等別具一格的設計，整棟建築完全沒有樑柱支撐，也無一處為90度牆面，顛覆了一般建築物的概念。\n" +
                      "歷經多年的建設，一座與人、與自然共生的劇場即將在臺中誕生。圓弧曲線，靜靜孵育眾人的期待；玻璃帷幕映著藍天，周邊環繞的綠地、流水成為城市的桃花源。「壺中居」歌劇院正面造型猶如曲線的酒壺，觀眾如酩酊般陶醉於節目的精彩，讚歎藝術無盡的可能壺面鑲有多個圓筒窗，夜晚隨燈光投射展現出優雅與溫暖．這裡是表演藝術的搖籃、是人們生活休憩與吸取新知的地方，更是中臺灣文化生活接受翻新的起點。這就是臺中國家歌劇院。\n\n" +
                      "由於臺中國家歌劇院興建難度極高，因此被建築業界稱為「全球最難蓋的房子」，而「曲牆」建築工法更獲得建築工法的世界專利認證。"));
        places.add(new Place("台中公園", R.drawable.park,"台中市北區雙十路一段65號",
                1.0f, 24.145157, 120.684526,
                "臺中公園自1903年(明治36年)10月28日創立以來，一直是臺中市重要的地標，公園內設有人工湖、亭臺樓閣、曲橋等優美造景，還有露天音樂臺、兒童遊戲區、網球場等多項遊樂設施，適合親子一同前往休閒運動，因位於臺中市區，交通方便，成為臺中市一個假日闔家出遊的熱門景點。\n\n" +
                      "在台灣的市區，要找到像臺中公園這樣綠木群集實不多見，這般的詩情畫意，不知要多久的養成，得以讓後人在疲累的時候，還能找到不花大錢、不需要特別開車跑到山間或溪邊的自然休憩處。\n\n" +
                      "對許多台中人來說，臺中公園是從小到大玩樂休息的好所在，不管運動、或是在湖心亭上划船，有家人相聚的溫馨場所，也有情侶訴說愛意的閒情角落；對旅客而言，臺中公園是台中的地標，是全臺灣首座的百年公園，保有古意盎然的遺蹟，也有地利之便，適合走趟歷史長廊後，前往熱鬧的夜市與車站相當方便，旅館、美食、小吃應有盡有，是絕佳動靜皆宜的行程景點之一。"));
        places.add(new Place("秋紅谷廣場", R.drawable.square,"台中市西屯區朝富路30號",
                0.5f, 24.168593, 120.639414,
                "不論甚麼時節，漫步在秋紅谷廣場都是個賞心悅目的享受！位於臺中市臺灣大道旁的秋紅谷廣場，是臺中地區特有的凹型市民休閒綠地，占地3公頃多的秋紅谷公園，擁有湖泊、紅樹、綠草坪、觀景橋，相當適合散步、約會、運動，白天有舒服的綠意；夜晚有著精采萬變的不夜燈火，開放迄今迅速成為臺中最火紅的景點之一。\n\n" +
                      "漫步於特殊的凹型廣場內，踩著以木屑鋪成的步道，柔軟舒適，還能聞到淡淡地木頭香，一旁清澈的湖水、翠綠的青苔，跳動的小魚兒，讓人心情雀躍起來，週邊種植朱蕉、垂柳、無患子、黃連木等植物，更兼具育教娛樂的功能。\n\n" +
                      "秋紅谷廣場所有的設計與考量都是以人本、綠能與滯洪為最大考量，不僅種植植物所需的灌溉水是以湖水供應，連回填所需用的土壤也是與水利署協調利用大里溪疏濬的土壤，成為全臺首座「下凹式」的生態公園；秋紅谷廣場的設立，是希望讓市民的生活更感輕鬆、從容，同時兼具景觀、生態、滯洪、排水與調節空氣品質等等的功能。"));
        places.add(new Place("高美濕地", R.drawable.sunset,"台中市清水區美堤街",
                1.0f, 24.312022, 120.549823,
                "高美濕地（高美野生動物保護區）位於清水大甲溪出海口南側，面積廣達701.3公頃，擁有豐富的天然資源，是國內少數幾處雁鴨集體繁殖區之一。曾在高美濕地棲息的鳥類有多達120餘種，為重要之生態保育區。目前已逐漸取代大肚溪口，成為喜好賞鳥人士的新興賞線。\n\n" +
                      " 高美濕地（高美野生動物保護區）的前身為高美海水浴場，從日治時期開始，海水浴場一直是遊客夏日的休閒勝地，隨著臺中港的啟用，高美海水浴場泥沙日漸淤積，導致遊客漸漸稀少，在不得已的情況下，只好宣告關閉，卻也因為泥沙的堆積，造就了今天生態豐富的高美濕地。\n\n" +
                      "高美自行車道是沿著清水大排、海堤至高美溼地，南起濱海橋、北至高美海堤防汛道路，全長3.3公里，車道旁緊鄰清水大排和高美溼地，除了可以鳥瞰整片高美溼地外，同時也是賞鳥、看夕陽的好地方，未來還可眺望西側海岸正在興建的風力發電機組，是個可以讓遊客旅遊休憩的好地方。"));
        places.add(new Place("國立自然科學博物館",R.drawable.museum,"台中市北區館前路1號",
                0.5f, 24.157042, 120.666041,
                "國立自然科學博物館，簡稱科博館，是位於臺灣臺中市北區的公立科學博物館，是中華民國國家十二項建設文化建設項下興建的首座科學博物館。\n\n"+
                        "館區由科學中心、太空劇場、生命科學廳、人類文化廳、地球環境廳與植物園所共同組成。\n\n"+
                        "館內內容豐富且深具教育意義，館外的園區也適合休閒踏青，故歷年來參觀人數平均居全臺博物館第二名，高達三百萬人次左右。"));
        places.add(new Place("道禾六藝文化館",R.drawable.place9,"403台中市西區林森路33號",
        0.5F,24.134369, 120.673977,
        "台中刑務所演武場簡稱刑武場，現名道禾六藝文化館，為市內僅存的日式武道館，功能為提供當時台中監獄服務的警官練武，2006年發生火災後，由文化局著手修復，現已開放民眾參觀。\n\n"+
                "主體建築左側為柔道場，右側為劍道場，後方則設置神龕，附屬的建築物其一是擺放燒毀前的房屋構件及相關歷史照片、文物等。\n\n"+
                "刑武場歷史原貌完整，極具保存價值，從不同角度欣賞著刑務所演武場，古色古香的建築讓人有穿越時空的感覺因近來復古懷舊風興盛，刑武場每到假日總是吸引許多團體參訪，是台中市區好攝客、外拍、婚紗最愛的取景地點。"));
        ListView lv  = (ListView)findViewById(R.id.lv_place);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = places.get(position).name;
                String addr = places.get(position).addr;
                int imgid = places.get(position).imgID;
                double px = places.get(position).Px;
                double py = places.get(position).Py;
                String info = places.get(position).info;
                float score = places.get(position).score;

                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Attraction.class);
                intent.putExtra("NAME", name);
                intent.putExtra("ID", imgid);
                intent.putExtra("PX", px);
                intent.putExtra("PY", py);
                intent.putExtra("INFO", info);
                intent.putExtra("SCORE", score);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mn_me:
                Intent t = new Intent();
                t.setClass(MainActivity.this, User.class);
                startActivity(t);
                break;
            case R.id.mn_set:
                Toast toast1 = Toast.makeText(MainActivity.this,
                        "工作分配:\n程式碼:葉昭甫\n" +
                        "創git team-project:林崇寶\n" +
                        "上台報告:詹亞豪", Toast.LENGTH_LONG);
                toast1.show();
                break;
        }
        return true;
    }

    public class PlaceArrayAdapter extends ArrayAdapter<Place> {
        Context context;
        public PlaceArrayAdapter(@NonNull Context context, ArrayList<Place> items) {
            super(context, 0, items);
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(context);
            LinearLayout itemlayout = null;
            if(convertView == null) {
                itemlayout = (LinearLayout)inflater.inflate(R.layout.placeitem, null);
            } else {
                itemlayout = (LinearLayout) convertView;
            }

            Place item = (Place) getItem(position);

            TextView tv_name = (TextView)itemlayout.findViewById(R.id.place_name);
            tv_name.setText(item.name);
            TextView tv_addr = (TextView)itemlayout.findViewById(R.id.place_addr);
            tv_addr.setText(item.addr);
            ImageView iv = (ImageView)itemlayout.findViewById(R.id.place_img);
            iv.setImageResource(item.imgID);
            return itemlayout;
        }

    }

}
