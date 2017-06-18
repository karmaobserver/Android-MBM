package com.mbm.ftn.mbm.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mbm.ftn.mbm.R;
import com.mbm.ftn.mbm.dao.CityDao;
import com.mbm.ftn.mbm.dao.NumberDao;
import com.mbm.ftn.mbm.dao.NumberListDao;
import com.mbm.ftn.mbm.dao.ProfileDao;
import com.mbm.ftn.mbm.dao.SurvivalTextDao;
import com.mbm.ftn.mbm.database.DatabaseManager;
import com.mbm.ftn.mbm.models.City;
import com.mbm.ftn.mbm.models.Number;
import com.mbm.ftn.mbm.models.NumberList;
import com.mbm.ftn.mbm.models.Profile;
import com.mbm.ftn.mbm.models.SurvivalText;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    NumberDao numberDao = null;
    NumberListDao numberListDao = null;
    CityDao cityDao = null;
    ProfileDao profileDao = null;
    SurvivalTextDao survivalTextDao = null;
    List<SurvivalText> textList = new ArrayList<SurvivalText>();
    List<Number> numberList = new ArrayList<Number>();
    List<NumberList> numberListList = new ArrayList<NumberList>();
    List<City> cityList = new ArrayList<City>();
    List<Profile> profileList = new ArrayList<Profile>();

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        initGui(newConfig);
      //  redrawButtons();
        initButtons();
    }

    private void redrawButtons() {
        ImageButton flashlightButton2 = (ImageButton) findViewById(R.id.button_topleft);
        flashlightButton2.invalidate();
        ImageButton flashlightButton3 = (ImageButton) findViewById(R.id.button_topright);
        flashlightButton3.invalidate();
        ImageButton survivalButton = (ImageButton) findViewById(R.id.button_bottomleft);
        survivalButton.invalidate();
        ImageButton importantNumbers = (ImageButton) findViewById(R.id.button_middleright);
        importantNumbers.invalidate();
        ImageButton sos = (ImageButton) findViewById(R.id.button_middleleft);
        sos.invalidate();
        ImageButton weather = (ImageButton) findViewById(R.id.button_bottomright);
        weather.invalidate();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initGui(getResources().getConfiguration());

        //Database init
        DatabaseManager.init(this);
        //databaseInit();
        //addToFirebase();
        firebaseSync();

        initButtons();

    }

    private void initButtons() {

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        ImageButton flashlightButton2 = (ImageButton) findViewById(R.id.button_topleft);
        flashlightButton2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Flashlight2Activity.class);
                startActivity(intent);
            }
        });

        ImageButton flashlightButton3 = (ImageButton) findViewById(R.id.button_topright);
        flashlightButton3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CompassActivity.class);
                startActivity(intent);
            }
        });

        ImageButton survivalButton = (ImageButton) findViewById(R.id.button_bottomleft);
        survivalButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SurvivalTextActivity.class);
                startActivity(intent);
            }
        });

        ImageButton importantNumbers = (ImageButton) findViewById(R.id.button_middleright);
        importantNumbers.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ImportantNumbersActivity.class);
                startActivity(intent);
            }
        });

        ImageButton sos = (ImageButton) findViewById(R.id.button_middleleft);
        sos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SosActivity.class);
                startActivity(intent);
            }
        });

        ImageButton weather = (ImageButton) findViewById(R.id.button_bottomright);
        weather.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), WeatherActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initGui(Configuration configuration) {

        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
           setContentView(R.layout.activity_main_landscape);

        } else if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main_portrait);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_gps:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void databaseInit() {

        numberDao = new NumberDao(this);
        numberListDao = new NumberListDao(this);
        cityDao = new CityDao(this);
        survivalTextDao = new SurvivalTextDao(this);
        profileDao = new ProfileDao(this);

        Profile profile1 = new Profile("BRAT", "Zoki", "Rodić", "+381451131313", "mak@gmail.com", "Pomagaj brate!!!", false);
        Profile profile2 = new Profile("Ćale", "Paja", "Pajtić", "+3821321", "paja@gmail.com", "Pomagaj ćale!!!", false);
        Profile profile3 = new Profile("FBI", "Frobi", "Fbić", "+38199977777", "fbi@gmail.com", "Dje ste Ameri?!", false);

        profileDao.create(profile1);
        profileDao.create(profile2);
        profileDao.create(profile3);

        profileList.add(profile1);
        profileList.add(profile2);
        profileList.add(profile3);


        City city1 = new City("Beograd");
        City city2 = new City("Novi Sad");
        City city3 = new City("Srbija");
        City city4 = new City("Niš");

        cityDao.create(city1);
        cityDao.create(city2);
        cityDao.create(city3);
        cityDao.create(city4);

        cityList.add(city1);
        cityList.add(city2);
        cityList.add(city3);
        cityList.add(city4);

        NumberList emergencyList = new NumberList("Hitne službe");
        numberListDao.create(emergencyList);
        Number number1 = new Number("Hitna pomoć", "194", "Hitne intervencije hitne pomoći", emergencyList, null, null, city3);
        Number number2 = new Number("Policija", "192", "Hitne intervencije policije", emergencyList, null, null, city3);
        Number number3 = new Number("Vatrogasci", "193", "Vatrogasna služba", emergencyList, null, "Omladinskih brigada 1, Beograd", city3);
        Number number4 = new Number("Gorska služba", "+38163466466", "Gorska služba spašavanja", emergencyList, "http://www.gss.rs/", null, city3);


        NumberList mobileOperatorsList = new NumberList("Mobilna telefonija");
        numberListDao.create(mobileOperatorsList);
        Number number5 = new Number("Vip mobile", "+381601234", null, mobileOperatorsList, "https://www.vipmobile.rs/privatni", "Omladinskih brigada 21, Beograd", city3);
        Number number6 = new Number("Telenor", "+3816390000", null, mobileOperatorsList, "https://www.telenor.rs/", "Omladinskih brigada 90, Beograd", city3);
        Number number7 = new Number("MTS", "+38164789", null, mobileOperatorsList, "https://www.mts.rs/", "Branislava Nusića", city3);


        NumberList healthCare = new NumberList("Zdravstvo");
        numberListDao.create(healthCare);
        Number number8 = new Number("Dom zdravlja Liman", "+381214807187", null, healthCare, null, null, city2);
        Number number9 = new Number("Dom zdravlja bistrica", "+38121401122", null, healthCare, null, null, city2);
        Number number10 = new Number("Kliničko bolnički centar", "+381214843484", null, healthCare, null, null, city2);
        Number number11 = new Number("Dečja bolnija", "+38121422699", "Bolnica za decu", healthCare, null, null, city2);
        Number number12 = new Number("Dispanzer", "+38121422699", null, healthCare, null, null, city2);
        Number number13 = new Number("Klinika za akušerstvo i ginekologiju", "+381214899222", null, healthCare, null, null, city2);
        Number number14 = new Number("Higijenski Zavod", "+38121422255", "Bolnica za decu", healthCare, null, null, city2);
        Number number15 = new Number("Noćna ambulanta Zemun", "+381112600192", "Svakog dana 19:00-07:00", healthCare, null, "Karađorđev trg 4", city1);
        Number number16 = new Number("Dežurna apoteka  Prvi Maj", "+381112643170", "svakog dana 00:00-24:00", healthCare, null, "Kralja Milana 9", city1);
        Number number17 = new Number("KBC Zvezdara", "+381113806969", null, healthCare, "http://www.kbczvezdara.rs/", "Dimitrija Tucovića 161", city1);

        NumberList transport = new NumberList("Saobraćaj");
        numberListDao.create(transport);
        Number number18 = new Number("Automoto Savez Srbije", "987", "Pomoć na putu, stanje na putevima, međunarodna dokumenta, saobraćajna...", transport, "http://www.amss.org.rs/", "Ruzveltova 18, Zvezdara", city3);
        Number number19 = new Number("Aerodorom Nikola Tesla", "+381112094444", null, transport, "http://www.beg.aero/", "Beograd", city3);
        Number number20 = new Number("Aerodrom Konstantin Veliki", "+38118585858", null, transport, "http://nis-airport.com/en/", "Vazduhoplovaca 24", city3);
        Number number21 = new Number("Autobuska stanica", "+38118255177", null, transport, null, null, city4);
        Number number22 = new Number("Železnička stanica", "+38118364625", null, transport, null, null, city4);
        Number number23 = new Number("Autobuska stanica", "0901111021", "Međumesna autobuska stanica Novog Sada", transport, "http://www.gspns.co.rs/", null, city2);
        Number number24 = new Number("Železnička stanica", "+38121443200", "Želeynice Srbije, stanica Novi Sad", transport, "http://www.zeleznicesrbije.com/", null, city2);
        Number number25 = new Number("Autobuska stanica", "+381112636299", "Beogradska autobuska stanica", transport, "http://www.bas.rs/", "Železnička 4", city1);
        Number number26 = new Number("Železnička stanica", "+38118364625", "Železnice Srbije, stanica Beograd", transport, "http://www.zeleznicesrbije.com/", "Savski trg 2", city1);

        numberListList.add(mobileOperatorsList);
        numberListList.add(healthCare);
        numberListList.add(transport);

        numberDao.create(number1);
        numberDao.create(number2);
        numberDao.create(number3);
        numberDao.create(number4);
        numberDao.create(number5);
        numberDao.create(number6);
        numberDao.create(number7);
        numberDao.create(number8);
        numberDao.create(number9);
        numberDao.create(number10);
        numberDao.create(number11);
        numberDao.create(number12);
        numberDao.create(number13);
        numberDao.create(number14);
        numberDao.create(number15);
        numberDao.create(number16);
        numberDao.create(number17);
        numberDao.create(number18);
        numberDao.create(number19);
        numberDao.create(number20);
        numberDao.create(number21);
        numberDao.create(number22);
        numberDao.create(number23);
        numberDao.create(number24);
        numberDao.create(number25);
        numberDao.create(number26);

        numberList.add(number1);
        numberList.add(number2);
        numberList.add(number3);
        numberList.add(number4);
        numberList.add(number5);
        numberList.add(number6);
        numberList.add(number7);
        numberList.add(number8);
        numberList.add(number9);
        numberList.add(number10);
        numberList.add(number11);
        numberList.add(number12);
        numberList.add(number13);
        numberList.add(number14);
        numberList.add(number15);
        numberList.add(number16);
        numberList.add(number17);
        numberList.add(number18);
        numberList.add(number19);
        numberList.add(number20);
        numberList.add(number21);
        numberList.add(number22);
        numberList.add(number23);
        numberList.add(number24);
        numberList.add(number25);
        numberList.add(number26);


        /*Survival Text Database*/
        SurvivalText s1 = new SurvivalText("Pribor za preživljavanje", "Nekoliko ključnih stvari može činiti bit borbe za preživljavanje.\n" +
                "Za to je potrebno prikupiti stvari prikazane na slici 1. Sve one\n" +
                "mogu se smjestiti u malu torbicu (kutiju i sl.) ili kutiju za\n" +
                "cigarete (tabakeru), koja je teško uočljiva. Stvorite naviku da\n" +
                "uvijek imate pribor uz sebe. Ne birajte ništa veliko jer može\n" +
                "biti neprikladno za nošenje, a tada najpotrebnije stvari mogu\n" +
                "ostati zaboravljene kod kuće. Mnogi ljudi koji sami savijaju\n" +
                "cigarete imaju takve kutijice. No ova naša tabakera mnogo je\n" +
                "korisnija, tako reći, može Vam spasiti život, dok pušači kreću\n" +
                "prema kraju svojega. Iskustva su pokazala da svaki dio\n" +
                "pribora zaslužuje svoje mjesto, iako je ponekad neki dio\n" +
                "korisniji u nekim situacijama nego drugi: udice za ribe, na\n" +
                "primjer, mogu biti nekorisne u džungli, ali su vrlo korisne u\n" +
                "pustinji.", "Nekoliko ključnih stvari može činiti bit borbe za preživljavanje.", R.drawable.ic_pribor);

        SurvivalText s2 = new SurvivalText("Noževi", "Nož je nezamjenjiv i neophodan u slučajevima preživljavanja. Ozbiljni avanturisti sigurno će imati\n" +
                "barem jedan uz sebe. Noževi su svakako opasni i mogu poslužiti kao oružje. Prilikom putovanja, a\n" +
                "posebice avionom, treba biti dobro zapakiran i neprimjetan (zajedno sa pilotskom i avionskom opremom)\n" +
                "te ga nije uputno vaditi u napetim i nezgodnim situacijama.\n" +
                "Izbor noža\n" +
                "Sklapajući nož sa više oštrica je koristan pribor, ali ako se namjerava nositi samo jedan nož,\n" +
                "najbolji je višenamjenski nož, koji će zadovoljiti sve potrebe efikasno i lako: od rezanja tanjih grana do\n" +
                "guljenja životinja i pripreme namirnica. Neki noževi imaju u svojoj dršci i kompas ili im je kompletna drška\n" +
                "šuplja tako da u nju stane i nešto od pribora za preživljavanje. U svakom slučaju te varijante zavisit će od\n" +
                "izdržljivosti šuplje drške, a kompas bi mogao brzo izgubiti svoju preciznost, nakon što bi se nožem\n" +
                "zasijecala tvrđa drveća. Ako se slučajno takav nož izgubi, izgubio se i pribor za preživljavanje.", "Nož je nezamjenjiv i neophodan u slučajevima preživljavanja.", R.drawable.ic_noz);

        SurvivalText s3 = new SurvivalText("Suočavanje sa kriznim situacijama", "Pri suočavanju sa kriznim situacijama najlakše je prepustiti se, doživjeti slom i sažaljevati se.\n" +
                "Nema nikakve koristi predavati se i \"zakapati glavu u pijesak\", nadati se da je to samo ružan san koji će\n" +
                "uskoro proći. Neće proći, a sa takvim razmišljanjem situacija će se još i pogoršavati. Samo pozitivan stav i\n" +
                "djelovanje su spas. Zdrava i dobro hranjena osoba može se fizički postaviti pred veliku dilemu, brinući se\n" +
                "o samopovjerenju. Čak i bolesna, ranjena te neodlučna osoba, može sigurno proći kroz takvu situaciju i\n" +
                "oporaviti se. Da bi tako bilo postoje određene stresne situacije koje treba prevladati.\n" +
                "Stresovi preživljavanja\n" +
                "Situacija preživljavanja stavit će Vas pod fizički i psihički pritisak. Morat ćete prebroditi neke ili sve\n" +
                "od navedenih stresova:\n" +
                "- strah i uznemirenost;\n" +
                "- bol, bolest i ranjavanje;\n" +
                "- hladnoća i / ili vrućina;\n" +
                "- žeđ, glad i umor;\n" +
                "- lišavanje sna (nesanica);\n" +
                "- dosada;\n" +
                "- samoća i izolacija.\n" +
                "Da li ste dorasli svemu tome ? Morate biti.", "Pri suočavanju sa kriznim situacijama najlakše je prepustiti se, doživjeti slom i sažaljevati se.", R.drawable.ic_krizne);

        SurvivalText s4 = new SurvivalText("Voda", "Voda je jedan od bitnih elemenata za život. Cijeli život praktički ovisi o vodi i svako živo biće je\n" +
                "nosi u sebi. Prosječan čovjek može preživjeti tri tjedna bez hrane, ali samo tri dana bez vode.\n" +
                "Voda je prioritet broj jedan. Nemojte čekati da Vam nestanu zalihe vode, a da je prije niste pokušali\n" +
                "negdje pronaći. Pohranite ono što imate i počnite što je moguće prije tražiti izvore za stvaranje novih\n" +
                "zaliha. Poželjna je svježa izvorska voda, iako sve vrste voda mogu biti sterilizirane kuhanjem ili uporabom\n" +
                "kemijskih prečistača. Tijelo čovjeka sadrži 75 % vode. Ona je održavatelj organizma pri svim\n" +
                "temperaturama, potrebna je pri održavanju bubrežne funkcije koja eliminira otpadne tvari te je u neku ruku\n" +
                "i vodič za živčane impulse. Tekućine u organizmu su ograničene. Gubitak vode u organizmu mora biti\n" +
                "nadomješten ili će stradati Vaše zdravlje i snaga.\n" +
                "Gubitak vode\n" +
                "Prosječan čovjek gubi 2 - 3 litre vode svaki dan, čak i oni koji se u hladu odmaraju gube 1 litru\n" +
                "vode. Samo disanjem se gubi tekućina, a gubitak putem disanja i znojenja nastaje u međusobnom omjeru\n" +
                "rada i okolne temperature. Povraćanje i proljev zbog bolesti uzrokuju daljnji gubitak tekućine. Sve to mora\n" +
                "biti nadomješteno kako se ne bi poremetila ravnoteža vode; bilo vodom za piće ili vodom iz hrane.", "Voda je jedan od bitnih elemenata za život", R.drawable.ic_voda);

        SurvivalText s5 = new SurvivalText("Kondenzacija", "Korijenje drveća i biljaka upija vlagu iz zemlje.\n" +
                "doseže vodene \"ploče\" (površine) na dubini od otprilike 15 metara, što je inače preduboko za kopanje. To\n" +
                "nemojte niti pokušavati. Pustite neka drvo izvlači vodu i vlagu, a Vi zavežite plastičnu vreću oko grane s\n" +
                "lišćem. Isparavanje iz listova proizvest će kondenzaciju i kapljice kondenzata sakupljat će se u dnu vreće.\n" +
                "Izaberite zdravu vegetaciju i bujne grane. Za granu privežite usta vreće kako bi donji kutevi vreće visjeli\n" +
                "prema dolje gdje će se sakupljati kondenzirana tekućina.\n" +
                "Stavljanje najlonske vrećice preko bilo kakve vegetacije na zemlji, također će sakupljati vlagu dobivenu\n" +
                "isparavanjem\n" +
                "Objesite vrećicu sa vrha ili poduprite štapom. Ne dozvolite da lišće dodiruje stijenke vrećice jer će\n" +
                "pokupiti kondenzirane kapi vode koje bi se trebale sakupljati u ispod napravljen kanalić obložen folijom.\n" +
                "Čak i odsječena vegetacija može proizvesti kondenzaciju kada se,stavljena u prostranu vreću, zagrije.\n" +
                "Lišće bilja treba odmaknuti od tla s kamenjem tako da se voda sakuplja ispod biljke i isto tako ne dozvoliti\n" +
                "da lišće dodiruje vreću. Oblikujte vrh vrećice, podupirući je štapom (iznutra). Uredite vrećicu sa neznatnim",
                "Korijenje drveća i biljaka upija vlagu iz zemlje, premda drveće može upijati i vodu korijenjem.", R.drawable.ic_kondenz);

        SurvivalText s6 = new SurvivalText("Zamke", "Većinu malog plema lakše je uloviti u zamku nego poći u lov na njih. Ako lovite\n" +
                "male životinje potrebne su relativno male zamke koje se lako mogu prikriti. Lov zamkama zahtijeva manje\n" +
                "vještine, što ostavlja dovoljno vremena za potragu za drugom hranom. Uvijek budite spremni kada imate\n" +
                "prednost i priliku uloviti životinju koja miruje. Puno je poznatih zamki koje rade na principu kompliciranih\n" +
                "mehanizama. Za njih treba vremena u postavljanju, što zahtijeva dodatni fizički napor. Čovjeku za\n" +
                "preživljavanje trebaju jednostavne zamke, lagane za konstrukciju i pamćenje načina izrade. Kako bilo,\n" +
                "zato što svaka životinja ima drugo stanište, bitne su sve vrste i oblici zamki. Ako jedna zamka zakaže, za\n" +
                "nju postoji i druga varijanta, što se iznalazi na temelju pokusa i pogrešaka.\n" +
                "Osobna prevencija (zaštita) čovjeka u preživljavanju, mora imati prednost nad humanitarnim\n" +
                "principima i na žalost neke od lakših zamki mogu uzrokovati patnju i mučenje životinje. Zamke koje će\n" +
                "brzo usmrtiti plijen kojem su namijenjene, možda davljenjem, mogu uloviti drugu životinju, na primjer, uz\n" +
                "pomoć grane drveta od koje je konstruirana i ostaviti je da se muči satima. Povremena i redovita provjera\n" +
                "zamki je bitna. Ostavljajući zamku neprovjerenu, mučenje životinje može se produžiti, a tako i rizik da ulov\n" +
                "ukrade životinja grabljivica, odnosno da se životinja lako oslobodi. Neke životinje moći će uništiti zamku ili\n" +
                "učiniti sve da se iz nje izvuku. Većina grešaka može se izbjeći proučavanjem životinjskih staništa. Također\n" +
                "je važan izbor mamca i mjesta za zamku. Ako jedna zamka ne uspije, pokušajte s drugom. BUDITE\n" +
                "STRPLJIVI. Za lov zamkom treba dosta vremena. I životinje mogu biti vrlo sumnjičave u prvo vrijeme, ali\n" +
                "se kasnije toga oslobode i ulete ravno u zamku. Čak i kada ste u pokretu, nekoliko jednostavnih zamki\n" +
                "koje se preko noći mogu postaviti, donijet će rezultate, a kada ćete raditi privremeni tabor, možete\n" +
                "postaviti cijeli \"lanac\" zamki. Što ih više postavite, veće su i šanse za ulov. Postavite što veći lanac zamki,\n" +
                "koliko možete nadzirati to područje.", "Većinu malog plena lakše je uloviti u zamku nego poći u lov na njih.", R.drawable.ic_zamka);

        SurvivalText s7 = new SurvivalText("Lov", "Jasno opažanje znakova divljeg života i poznavanje životinja koje lovite potrebno je lovcu koliko i\n" +
                "vješto traganje i označavanje na području. To olakšava čovjeku da se snađe na pravom mjestu i stekne\n" +
                "prednost na terenu. Po terenu se valja kretati što nečujnije, polako te povremeno zastati. Težinu tijela\n" +
                "prebacujte na onu nogu kojom ne iskoračujete, tako da možete procijeniti slijedeći korak, odnosno\n" +
                "podlogu na koju ćete stati prije nego prebacite težinu. Tako se izbjegava spoticanje i smanjuje buka\n" +
                "gaženja po raslinju i lomljenja grančica. Brzo i iznenadno kretanje preplašit će divljač. Njušite zrak i\n" +
                "osluškujte. Lovite u smjeru suprotnom od vjetra, a ako baš morate, krećite se bočno od smjera vjetra.\n" +
                "Idealno vrijeme za lov je u zoru kada je većina divljači troma. Životinje su također tromije u predvečerje, ali\n" +
                "isto tako se smanjuje dnevna svjetlost te tako morate biti sigurni da poznajete teren i put natrag do svoga\n" +
                "tabora. U području koje dobro poznajete to neće biti problem, pogotovo ako je noću nebo vedro i ako je\n" +
                "mjesečina, odnosno kada je vidljivost dosta dobra.\n" +
                "Ako lovite noću, krenite najmanje jedan sat prije sumraka tako da se oči priviknu na smanjenje\n" +
                "svjetlosti i na noćne uvjete - premda će životinje najvjerojatnije bolje vidjeti od Vas. Kada lovite po danu,\n" +
                "pokušajte se kretati uzbrdo ujutro (ako je takva konfiguracija terena) i vraćati se u tabor poslijepodne.\n" +
                "Promatranje životinjskih tragova bit će lakše ako se krećete uzbrdo jer su tako tragovi bliži razini očiju.\n" +
                "Toplotne struje pod zemljom potpomognute toplinom tijekom dana nose miris divljači prema gore te tako\n" +
                "pri povratku nizbrdo nailazite na taj miris. Nakon isteka dana, lova i traganja, silaženje oduzima manje\n" +
                "energije nego penjanje uzbrdo te ćete se lakše kretati.\n" +
                "Ako se krećete ispravno, u većini slučajeva divljač Vas neće primijetiti. Ako Vas životinja načas\n" +
                "opazi, ostanite na mjestu. Možda ste prvi čovjek kojeg je životinja vidjela i bit će više radoznala nego\n" +
                "uplašena. Ostanite mirni i nepomični sve dok divljač ne skrene pogled ili se nastavi hraniti. Izbjegavajte\n" +
                "velike životinje kao što su medvjedi, osim ako ste čvrsto uvjereni da ih možete ubiti prvim pogotkom: u\n" +
                "protivnom postat ćete lovina umjesto lovca.\n" +
                "Približite se što je više moguće bez otkrivanja svoje nazočnosti te zauzmite čvrstu i sigurnu\n" +
                "poziciju, koja pruža pogled na šire područje. Direktan pogodak u glavu životinje vrlo je učinkovit, ali i\n" +
                "riskantan osim ako ste blizu životinji koja miruje. Ciljanje straga u prednji dio ramena, dobra je meta. Čvrst\n" +
                "i precizan udarac, bacit će većinu životinja. Loš udarac može značiti nepotrebnu agoniju životinje i dug put\n" +
                "kojim ćete je potom slijediti. Ako životinja padne nakon prvog udarca, pričekajte pet minuta prije nego se\n" +
                "približite. Ostanite na mjestu i promatrajte. Ako životinja nije mrtva, a krvari, gubitak krvi dodatno će je\n" +
                "oslabiti, a kada se približite ona neće biti u stanju pobjeći. Ako je životinja ranjena i nastavlja se kretati,\n" +
                "pričekajte 15 minuta prije nego je počnete slijediti. Ako krenete odmah za njom može se desiti da ćete je\n" +
                "slijediti cijeli dan.",
                "Jasno opažanje znakova divljeg života i poznavanje životinja koje lovite potrebno je lovcu.", R.drawable.ic_lov);

        SurvivalText s8 = new SurvivalText("Oružje", "Luk i strijela\n" +
                "Najučinkovitiji su od improviziranog oružja. Luk i strijelu je lako napraviti. Potrebno je malo\n" +
                "vremena za postizanje vještine rukovanja. Najbolje drvo za luk je tzv. “sezonsko”, što ne znači da ćete\n" +
                "uvijek uspješno izraditi luk od takvog drveta. Ako namjeravate na nekom području ostati nekoliko mjeseci,\n" +
                "sezonsko drvo može zastarjeti, odnosno preživjeti sezonu, stoga je poželjno napraviti nekoliko lukova od\n" +
                "različitog drveta u sezoni, pošto ona koja su zastarjela gube svoju elastičnost.\n" +
                "Za takve prilike četinjače su idealne - svi stari engleski lukovi načinjeni su od takve vrste drveta. Postoji\n" +
                "pet vrsta četinjača koje se distribuiraju sjevernom hemisferom, ali između sebe nemaju puno zajedničkog.\n" +
                "Zimzeleno drvo, hrast, bijeli brijest, cedar, breza, vrba i kukuta su za to dobar nadomjestak.", "Luk i strijela\n" +
                "Najučinkovitiji su od improviziranog oružja.", R.drawable.ic_oruzije);


        survivalTextDao.create(s1);
        survivalTextDao.create(s2);
        survivalTextDao.create(s3);
        survivalTextDao.create(s4);
        survivalTextDao.create(s5);
        survivalTextDao.create(s6);
        survivalTextDao.create(s7);
        survivalTextDao.create(s8);

        textList.add(s1);
        textList.add(s2);
        textList.add(s3);
        textList.add(s4);
        textList.add(s5);
        textList.add(s6);
        textList.add(s7);
        textList.add(s8);

        Log.d("DATABASE_INIT", "Database has initilize");


    }

    private void addToFirebase() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference firebase = database.getReference("firebase");
        firebase.child("text").removeValue();
        firebase.child("numbers").removeValue();
        firebase.child("numberLists").removeValue();
        firebase.child("cities").removeValue();
        firebase.child("profiles").removeValue();

        DatabaseReference textDb = firebase.child("texts");
        textDb.setValue(textList);

        DatabaseReference numberDb = firebase.child("numbers");
        numberDb.setValue(numberList);

        DatabaseReference numberListDb = firebase.child("numberLists");
        numberListDb.setValue(numberListList);

        DatabaseReference cityListDb = firebase.child("cities");
        cityListDb.setValue(cityList);

        DatabaseReference profileListDb = firebase.child("profiles");
        profileListDb.setValue(profileList);
    }


    private void firebaseSync() {

        numberDao = new NumberDao(this);
        numberListDao = new NumberListDao(this);
        cityDao = new CityDao(this);
        survivalTextDao = new SurvivalTextDao(this);
        profileDao = new ProfileDao(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference firebase = database.getReference("firebase");

        DatabaseReference textDb = firebase.child("texts");
        textDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                SurvivalText st = dataSnapshot.getValue(SurvivalText.class);

                if (survivalTextDao.findById(st.getId()) == null) {
                    survivalTextDao.create(st);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                SurvivalText st = dataSnapshot.getValue(SurvivalText.class);
                survivalTextDao.update(st);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                SurvivalText st = dataSnapshot.getValue(SurvivalText.class);
                survivalTextDao.delete(st);

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference numberDb = firebase.child("numbers");
        numberDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Number n = dataSnapshot.getValue(Number.class);
                if (numberDao.findById(n.getId()) == null) {
                    numberDao.create(n);
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Number n = dataSnapshot.getValue(Number.class);
                numberDao.update(n);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Number n = dataSnapshot.getValue(Number.class);
                numberDao.delete(n);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference numberListDb = firebase.child("numberLists");
        numberListDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                NumberList nl = dataSnapshot.getValue(NumberList.class);

                if (numberListDao.findById(nl.getId()) == null) {
                    numberListDao.create(nl);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                NumberList nl = dataSnapshot.getValue(NumberList.class);
                numberListDao.update(nl);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                NumberList nl = dataSnapshot.getValue(NumberList.class);
                numberListDao.delete(nl);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference cityListDb = firebase.child("cities");
        cityListDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                City c = dataSnapshot.getValue(City.class);
                if (cityDao.findById(c.getId()) == null) {
                    cityDao.create(c);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                City c = dataSnapshot.getValue(City.class);
                cityDao.update(c);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                City c = dataSnapshot.getValue(City.class);
                cityDao.delete(c);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        DatabaseReference profileListDb = firebase.child("profiles");
        profileListDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Profile p = dataSnapshot.getValue(Profile.class);
                if (profileDao.findById(p.getId()) == null) {
                    profileDao.create(p);
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Profile p = dataSnapshot.getValue(Profile.class);
                profileDao.update(p);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Profile p = dataSnapshot.getValue(Profile.class);
                profileDao.delete(p);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

}