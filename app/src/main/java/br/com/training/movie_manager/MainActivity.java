package br.com.training.movie_manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionMenuView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionMenuView bottomBar = (ActionMenuView) findViewById(R.id.toolbar_bottom);

        Menu bottomMenu = (Menu) bottomBar.getMenu();

        getMenuInflater().inflate(R.menu.menu_main, bottomMenu);

        for (int i = 0; i < bottomMenu.size(); i++){
            bottomMenu.getItem(i).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    return onOptionsItemSelected(item);
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_home:
                HomeFragment homeFragment = HomeFragment.newInstance();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, homeFragment).commit();
                break;
            case R.id.action_search:
                SearchFragment searchFragment= SearchFragment.newInstance();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, searchFragment).commit();
                break;
            case R.id.action_fav:
                FavoriteFragment favoriteFragment = FavoriteFragment.newInstance();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, favoriteFragment).commit();
                break;
            case R.id.action_more:
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(menuItem);
    }
}
