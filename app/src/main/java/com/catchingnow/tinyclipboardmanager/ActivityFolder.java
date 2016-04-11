package com.catchingnow.tinyclipboardmanager;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by Jayson on 2016-03-29.
 */
public class ActivityFolder extends ActionBarActivity { //TODO maybe change to MyActionBarActivity

    /*
    TODO variables go here
    --> maybe Toolbar variable (from MyActionBarActivity)
     */
    private ImageButton addClipButton;
    private Storage database;
    private FolderObject currentFolder;

    private ArrayList<ClipObject> clips;
    private Date lastStorageUpdate = null;
    private boolean clickToCopy = true;
    protected boolean isStarred = false;
    private static int TRANSLATION_FAST = 400;
    private RecyclerView mRecList;
    private LinearLayout mRecLayout;
    private ClipCardAdapter clipCardAdapter;
    private LinearLayoutManager linearLayoutManager;
    protected Toolbar mToolbar;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_folder);
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        Toast.makeText(ActivityFolder.this,
                "Folder button has been clicked!", Toast.LENGTH_SHORT).show();

        context = this;
        addClipButton = (ImageButton) findViewById(R.id.main_fab);
        database = Storage.getInstance(this); //get the database
        clips = new ArrayList<ClipObject>();

        //TODO in order to display only the contents in the folder, add a getFolderClips() to storage

        String folderName = intent.getStringExtra("folderObjName");
        List<FolderObject> listOfFolders = database.getFolderHistory();

        //search in the database for the folder in question
        for(int i = 0; i < listOfFolders.size(); i++){
            if(listOfFolders.get(i).getName().equals(folderName)) {
                currentFolder = listOfFolders.get(i); //store the folder in the variable 'currentFolder'
            }
        }

        initView();
        setView();

        /********************************************TESTING CODE**************************************/
//        Log.v("TEST: ", currentFolder.getName()); //test that the correct folder was opened
//
//        if(!currentFolder.getFolderContents().isEmpty()){
//
//
//            ClipObject cpObj = (ClipObject)currentFolder.getFolderContents().get(0);
////
//            if(cpObj != null)
//                Log.v("TEST: ", cpObj.getText());
//        }

        /**********************************************************************************************/

    }


    //TODO implement code that makes the add button work properly

    public void addButtonOnClick(View view){
        //TODO call modifyFolder for testing purposes
        //TODO in actual practice: call ActivityEditor and add the clip object to the database then
        Toast.makeText(ActivityFolder.this,
                "Add button has been clicked!", Toast.LENGTH_SHORT).show();

        Intent openEditorIntent = new Intent(context, ActivityEditor.class);
        openEditorIntent.putExtra("isFolderClip", true); //boolean to signal that it is a folder clip
        openEditorIntent.putExtra("folderName", currentFolder.getName()); //send the name of the folder
        startActivity(openEditorIntent);


//        /**************************************TESTING CODE ******************************************/
//        ArrayList<ClipObject>clipArray = new ArrayList<ClipObject>();
//        clipArray.add(new ClipObject("eyyy", new Date()));
//        clipArray.add(new ClipObject("nah", new Date()));
//        clipArray.add(new ClipObject("meow", new Date()));
//
//        database.modifyFolder(currentFolder.getName(), currentFolder.getName(), clipArray);
        /**********************************************************************************************/
    }




    //**********ALL THE CRAP NEEDED TO MAKE THE FOLDERS SHOW THEIR CLIPS**************************/
    protected void setView() {

        if (database.getLatsUpdateDate() == lastStorageUpdate) return;
        lastStorageUpdate = database.getLatsUpdateDate();

        //get clips
        //clips = currentFolder.getFolderContents();

        clips.add(new ClipObject("eyyy", new Date()));
        clips.add(new ClipObject("nah", new Date()));
        clips.add(new ClipObject("meow", new Date()));

        //set view
        clipCardAdapter = new ClipCardAdapter(clips, this);
        mRecList.setAdapter(clipCardAdapter);

        setItemsVisibility();
    }

    public class ClipCardAdapter extends RecyclerView.Adapter<ClipCardAdapter.ClipCardViewHolder> {
        private Context context;
        private List<ClipObject> clipObjectList;
        private boolean allowAnimate = true;

        public ClipCardAdapter(List<ClipObject> clipObjectList, Context context) {
            this.context = context;
            this.clipObjectList = clipObjectList;
            DisplayMetrics displaymetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    allowAnimate = false;
                }
            }, 100);
        }

        @Override
        public int getItemCount() {
            return clipObjectList.size();
        }


        @Override
        public void onBindViewHolder(final ClipCardViewHolder clipCardViewHolder, int i) {
            final ClipObject clipObject = clipObjectList.get(i);
            clipCardViewHolder.vDate.setText(MyUtil.getFormatDate(context, clipObject.getDate()));
            clipCardViewHolder.vTime.setText(MyUtil.getFormatTime(context, clipObject.getDate()));
            clipCardViewHolder.vText.setText(MyUtil.stringLengthCut(clipObject.getText()));
            if (clipObject.isStarred()) {
                clipCardViewHolder.vStarred.setImageResource(R.drawable.ic_action_star_yellow);
                clipCardViewHolder.vBackground.removeAllViews();
                clipCardViewHolder.vBackground.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            } else {
                clipCardViewHolder.vStarred.setImageResource(R.drawable.ic_action_star_outline_grey600);
            }

            if (clickToCopy) {
                addClickStringAction(context, clipObject, ClipObjectActionBridge.ACTION_COPY, clipCardViewHolder.vText);
                addLongClickStringAction(context, clipObject, ClipObjectActionBridge.ACTION_EDIT, clipCardViewHolder.vText);
            } else {
                addClickStringAction(context, clipObject, ClipObjectActionBridge.ACTION_EDIT, clipCardViewHolder.vText);
                addLongClickStringAction(context, clipObject, ClipObjectActionBridge.ACTION_COPY, clipCardViewHolder.vText);

            }
            addClickStringAction(context, clipObject, ClipObjectActionBridge.ACTION_SHARE, clipCardViewHolder.vShare);

            //setActionIcon(clipCardViewHolder.vShare);

            clipCardViewHolder.vStarred.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    database.changeClipStarStatus(clipObject);
                    //clipObject.setStarred(!clipObject.isStarred());
                    if (clipObject.isStarred()) {
                        clipCardViewHolder.vStarred.setImageResource(R.drawable.ic_action_star_yellow);
                    } else {
                        clipCardViewHolder.vStarred.setImageResource(R.drawable.ic_action_star_outline_grey600);
                        if (isStarred) {
                            //remove form starred list.
                            remove(clipObject);
                        }
                    }
                }
            });

            setAnimation(clipCardViewHolder.vMain, i);

        }


        @Override
        public ClipCardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.activity_main_card, viewGroup, false);

            return new ClipCardViewHolder(itemView);
        }

        public void add(int position, ClipObject clipObject) {
            clipObjectList.add(position, clipObject);
            notifyItemInserted(position);
            setItemsVisibility();
        }

        public void remove(ClipObject clipObject) {
            int position = clipObjectList.indexOf(clipObject);
            if (position == -1) return;
            remove(position);
        }

        public void remove(String clipString) {
            for (ClipObject clipObject : clipObjectList) {
                if (clipObject.getText().equals(clipString)) {
                    remove(clipObject);
                    return;
                }
            }
        }

        public void remove(int position) {
            clipObjectList.remove(position);
            notifyItemRemoved(position);
            setItemsVisibility();
        }

        private void setAnimation(final View viewToAnimate, int position) {
            //animate for list fade in
            if (!allowAnimate) {
                return;
            }
            viewToAnimate.setVisibility(View.INVISIBLE);
            final Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
            animation.setDuration(TRANSLATION_FAST);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    viewToAnimate.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewToAnimate.startAnimation(animation);
                }
            }, (position + 2) * 60);
        }


        public class ClipCardViewHolder extends RecyclerView.ViewHolder {
            protected TextView vTime;
            protected TextView vDate;
            protected TextView vText;
            protected ImageButton vStarred;
            protected ImageButton vShare;
            protected LinearLayout vBackground;
            protected View vMain;

            public ClipCardViewHolder(View v) {
                super(v);
                vTime = (TextView) v.findViewById(R.id.activity_main_card_time);
                vDate = (TextView) v.findViewById(R.id.activity_main_card_date);
                vText = (TextView) v.findViewById(R.id.activity_main_card_text);
                vStarred = (ImageButton) v.findViewById(R.id.activity_main_card_star_button);
                vShare = (ImageButton) v.findViewById(R.id.activity_main_card_share_button);
                vBackground = (LinearLayout) v.findViewById(R.id.main_background_view);
                vMain = v;
            }
        }

    }

    private void setItemsVisibility() {
        if (clipCardAdapter.getItemCount() == 0) {
            mRecLayout.setVisibility(View.INVISIBLE);
            //Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
            //mRecLayout.setAnimation(animation);
        } else {
            mRecLayout.setVisibility(View.VISIBLE);
            //Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
            //mRecLayout.setAnimation(animation);
        }
    }

    protected void addClickStringAction(final Context context, final ClipObject clipObject, final int actionCode, View button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openIntent = new Intent(context, ClipObjectActionBridge.class)
                        .putExtra(Intent.EXTRA_TEXT, clipObject.getText())
                        .putExtra(ClipObjectActionBridge.STATUE_IS_STARRED, clipObject.isStarred())
                        .putExtra(ClipObjectActionBridge.ACTION_CODE, actionCode);
                context.startService(openIntent);
            }
        });
    }

    protected void addLongClickStringAction(final Context context, final ClipObject clipObject, final int actionCode, View button) {
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                v.playSoundEffect(0);
                Intent openIntent = new Intent(context, ClipObjectActionBridge.class)
                        .putExtra(Intent.EXTRA_TEXT, clipObject.getText())
                        .putExtra(ClipObjectActionBridge.STATUE_IS_STARRED, clipObject.isStarred())
                        .putExtra(ClipObjectActionBridge.ACTION_CODE, actionCode);
                context.startService(openIntent);
//                if (isFromNotification) {
//                    moveTaskToBack(true);
//                }
                return true;
            }
        });
    }

    private void initView() {
        //init View

        mRecLayout = (LinearLayout) findViewById(R.id.recycler_layout);
        mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        mRecLayout.removeAllViewsInLayout();
        LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.view_main_recycler, mRecLayout, true);
        mRecList = (RecyclerView) findViewById(R.id.cardList);
        mRecList.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecList.setLayoutManager(linearLayoutManager);

    }
    /********************************************************************************************************/

}
