package goodtogo.com.myquotes;


import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import goodtogo.com.myquotes.fr.AllFragment;
import goodtogo.com.myquotes.fr.AngryFragment;
import goodtogo.com.myquotes.fr.LikeFragment;
import goodtogo.com.myquotes.fr.LoveFragment;
import goodtogo.com.myquotes.fr.SadFragment;
import goodtogo.com.myquotes.fr.TextViewFragment;

public class MainActivity extends AppCompatActivity {

    private static final String KEY = "PASS_KEY_NAME";
    private Fragment currentFragment;

    BottomBar bottomBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        final AllFragment fragment = new AllFragment();
        //TextViewFragment fragment = new TextViewFragment();
        fragmentTransaction.add(R.id.contentContainer, fragment);
        fragmentTransaction.commit();

        currentFragment = fragment;

        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                String title = "";
                if (tabId == R.id.tab_love) {
                    // The tab with id R.id.tab_favorites was selected,
                    // change your content accordingly.
                    zhow("This is Love");
                    title = "love_relationship.json";
                    currentFragment= new LoveFragment() ;

                }
                else if (tabId == R.id.tab_idea){
                    zhow("This is Angry");
                    title = "idea.json";
                    currentFragment = new LoveFragment();

                }
                else if (tabId == R.id.tab_about){
                    zhow("This is Like");
                    //title= "other.json";
                    currentFragment = new TextViewFragment();
                }

                else if (tabId == R.id.tab_inspiration){
                    zhow("This is inspiration");

                    currentFragment = new LoveFragment();
                    title = "education.json";
                }else if(tabId == R.id.tab_all){
                    zhow("This is All");
                    currentFragment = new AllFragment();
                }
                else {
                    zhow("Nothing");
                    // = new AllFragment();
                }
                commitFragment(currentFragment, title);
            }
        });

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
            if (tabId == R.id.tab_love) {
                // The tab with id R.id.tab_favorites was selected,
                // change your content accordingly.
                zhow("This is reLove");

            }
//            else if (tabId == R.id.tab_angry){
//                zhow("This is reAngry");
//            }
//            else if (tabId == R.id.tab_like){
//                zhow("This is reLike");
//            }

//            else if (tabId == R.id.tab_sad){
//            zhow("This is reSad");
//            }
            else if (tabId == R.id.tab_all){
                zhow("reAll");
            }
            else
                zhow("reNothing");

            }
        });
    }

    private void zhow(String s){
        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    public void commitFragment(Fragment f, String title){
        Bundle bundle = new Bundle();
        if (title == null || title.equals("") )
            title ="other.json";
        bundle.putString(KEY, title);

//        FragmentManager fragmentManager = getFragmentManager();
//        //fragmentManager.beginTransaction().remove(currentFragment).commit();
//
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id.contentContainer, f);
//        f.setArguments(bundle);
//        fragmentTransaction.commit();
        FragmentManager fragmentManager = getFragmentManager();
        f.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.contentContainer, f)
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.contentContainer);
        //if (bottomBar.getCurrentTabId() == R.id.tab_all ){
        //    confirmExit();
       // }

        if (!(fragment instanceof AllFragment)){
            bottomBar.setDefaultTab(R.id.tab_all);

            commitFragment(new AllFragment(), "");
        }else {
            confirmExit();
        }

        /*if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();

            //if(currentFragment != )
            commitFragment(new AllFragment(), "");
            bottomBar.setDefaultTab(R.id.tab_all);
        }*/

    }


    private void confirmExit(){


        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle(null);
        alertBuilder.setMessage(null);
        alertBuilder.setCancelable(true);

        View v = this.getLayoutInflater().inflate(R.layout.exit_layout, null);
        alertBuilder.setView(v);

        final AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();

        ImageButton ibCancel = (ImageButton) v.findViewById(R.id.ibCashInCancel);
        ibCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
            }
        });



        Button btnConfirm = (Button) v.findViewById(R.id.btnYes);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.finish();
            }
        });
        Button btnNo = (Button) v.findViewById(R.id.btnNo);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });


    }
}
