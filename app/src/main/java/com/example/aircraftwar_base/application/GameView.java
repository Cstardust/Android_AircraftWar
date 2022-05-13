package com.example.aircraftwar_base.application;

import static com.example.aircraftwar_base.application.ImageManager.BOSS_ENEMY_IMAGE;
import static com.example.aircraftwar_base.application.ImageManager.CLASSNAME_IMAGE_MAP;
import static com.example.aircraftwar_base.application.ImageManager.ELITE_ENEMY_IMAGE;
import static com.example.aircraftwar_base.application.ImageManager.ENEMY_BULLET_IMAGE;
import static com.example.aircraftwar_base.application.ImageManager.HERO_BULLET_IMAGE;
import static com.example.aircraftwar_base.application.ImageManager.HERO_IMAGE;
import static com.example.aircraftwar_base.application.ImageManager.MOB_ENEMY_IMAGE;
import static com.example.aircraftwar_base.application.ImageManager.get;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.Image;
import android.os.Build;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.aircraftwar_base.R;
import com.example.aircraftwar_base.aircraft.AbstractAircraft;
import com.example.aircraftwar_base.aircraft.BossEnemy;
import com.example.aircraftwar_base.aircraft.EliteEnemy;
import com.example.aircraftwar_base.aircraft.HeroAircraft;
import com.example.aircraftwar_base.aircraft.MobEnemy;
import com.example.aircraftwar_base.basic.AbstractFlyingObject;
import com.example.aircraftwar_base.bullet.BaseBullet;
import com.example.aircraftwar_base.bullet.EnemyBullet;
import com.example.aircraftwar_base.bullet.HeroBullet;
import com.example.aircraftwar_base.craftFactory.BossEnemyFactory;
import com.example.aircraftwar_base.craftFactory.EliteEnemyFactory;
import com.example.aircraftwar_base.craftFactory.MobEnemyFactory;
import com.example.aircraftwar_base.reward.AbstractReward;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

// 此类会开启一个绘画线程
public class GameView extends SurfaceView
        implements SurfaceHolder.Callback,Runnable{
//  界面绘画相关
    public static int screenWidth = 480, screenHeight = 800;
    private int backGroundTop;
    boolean mbLoop = false; //  控制绘画线程的标志位
    private SurfaceHolder mSurfaceHolder;
    private Canvas canvas;  //  绘图的画布
    private Paint mPaint;   //  画笔

//  游戏数据相关
    //  成绩
    private int score;
    //  飞机集合
    private final HeroAircraft heroAircraft;
    private final List<AbstractAircraft> enemyAircrafts;
    //  子弹集合
    protected final List<BaseBullet> heroBullets;
    protected final List<BaseBullet> enemyBullets;
    //  奖品集合
    protected final List<AbstractReward> leftRewards;
    //  最大数量
    private int enemyMaxNumber = 5;

    //  Boss相关
    protected int bossLimit = 1;
    protected int bossNum = 1;
    protected int bosssX = 2;
    protected int bosssY = 0;
    protected int bossHp = 300;
    //  Boss计数：用于计算boss什么时候出现。
    protected int cntBoss = 0;
    protected int recordOfBoss = 0;
    protected int bossThreshold = 100;  //  的多少分时出现

    //   子弹计时器
    protected Map<String,Double> shootCycle = new LinkedHashMap<String,Double>(){
        {
            put("hero",0.0);
            put("enemy",0.0);
        }
    };
    //  飞机计时器
    protected Map<String,Double> airCycle = new LinkedHashMap<String,Double>(){
        {
            put("normal",0.0);
            put("elite",0.0);
        }
    };
    //  子弹发射计时单位    不要变他 想改变子弹发射频率可以去变子弹周期
    protected final int ShootInterval = 40;
    //  飞行计时单位       不要变他 想改变飞机出现频率可以去变飞行周期
    protected final int airInterval = 40;
    //  各个飞机射击周期
    protected Map<String,Double> shootDuration = new LinkedHashMap<String,Double>(){
        {
            put("hero",700.0);
            put("enemy",1500.0);
        }
    };
    //  各个飞机飞行周期
    protected Map<String,Double> airDuration = new LinkedHashMap<String,Double>(){
        {
            put("normal",500.0);
            put("elite",1200.0);
        }
    };

    //  初始化View
    public GameView(Context context) {
        super(context);

        //  加载图片资源
        load_img();

        //  设置绘画界面相关
        mbLoop = true;          //  循环绘制画面
        mPaint = new Paint();   //  设置画笔
        mSurfaceHolder = this.getHolder();      //  ...
        mSurfaceHolder.addCallback(this);       //  ...
        this.setFocusable(true);                //  ...

        //  初始化 各种飞机集合
        //  英雄机
        int heroHp = 500;
        int heroPower = 100;
        int heroNum = 1;
        heroAircraft = HeroAircraft.getHeroAircraft(GameView.screenWidth / 2, GameView.screenHeight-ImageManager.HERO_IMAGE.getHeight() ,
                0, 0, heroHp,heroPower,-1,heroNum);

        //  精英敌机和普通敌机
        enemyAircrafts = new LinkedList<>();

        //  初始化子弹集合
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();

        //  初始化道具集合
        leftRewards = new LinkedList<>();

        //  设置鼠标监听
        new HeroController(this,heroAircraft);
    }

    //  加载ImageManager中的图片资源
    public void load_img()
    {
        ImageManager.BACKGROUND_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.bg);

        ImageManager.HERO_IMAGE = BitmapFactory.decodeResource(getResources(),R.drawable.hero);
        ImageManager.ELITE_ENEMY_IMAGE = BitmapFactory.decodeResource(getResources(),R.drawable.elite);
        ImageManager.MOB_ENEMY_IMAGE = BitmapFactory.decodeResource(getResources(),R.drawable.mob);
        ImageManager.BOSS_ENEMY_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.boss);

        ImageManager.HERO_BULLET_IMAGE = BitmapFactory.decodeResource(getResources(),R.drawable.bullet_hero);
        ImageManager.ENEMY_BULLET_IMAGE = BitmapFactory.decodeResource(getResources(),R.drawable.bullet_enemy);

        ImageManager.BLOOD_REWARD_IMAGE = BitmapFactory.decodeResource(getResources(),R.drawable.prop_blood);
        ImageManager.BOMB_REWARD_IMAGE = BitmapFactory.decodeResource(getResources(),R.drawable.prop_bomb);
        ImageManager.BULLET_REWARD_IMAGE = BitmapFactory.decodeResource(getResources(),R.drawable.prop_bullet);

        CLASSNAME_IMAGE_MAP.put(HeroAircraft.class.getName(), HERO_IMAGE);
        CLASSNAME_IMAGE_MAP.put(EliteEnemy.class.getName(),ELITE_ENEMY_IMAGE);
        CLASSNAME_IMAGE_MAP.put(MobEnemy.class.getName(), MOB_ENEMY_IMAGE);
        CLASSNAME_IMAGE_MAP.put(BossEnemy.class.getName(),BOSS_ENEMY_IMAGE);

        CLASSNAME_IMAGE_MAP.put(HeroBullet.class.getName(), HERO_BULLET_IMAGE);
        CLASSNAME_IMAGE_MAP.put(EnemyBullet.class.getName(), ENEMY_BULLET_IMAGE);
    }

    //  画飞机、子弹、道具、得分等
    public void draw(){
        //通过SurfaceHolder对象的lockCanvans()方法，我们可以获取当前的Canvas绘图对象
        canvas = mSurfaceHolder.lockCanvas();
        if(mSurfaceHolder == null || canvas == null){
            return;
        }
        mPaint.setAntiAlias(true);
        canvas.drawRect(0,0,screenWidth, screenHeight, mPaint);

        //  画滚动背景
        canvas.drawBitmap(ImageManager.BACKGROUND_IMAGE,0,this.backGroundTop-ImageManager.BACKGROUND_IMAGE.getHeight(),mPaint);
        canvas.drawBitmap(ImageManager.BACKGROUND_IMAGE,0,this.backGroundTop,mPaint);
        backGroundTop += 1;
        if(backGroundTop==screenHeight){
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        //  画子弹
        paintImageWithPositionRevised(canvas,enemyBullets);
        paintImageWithPositionRevised(canvas,heroBullets);
        //  画所有敌机
        paintImageWithPositionRevised(canvas,enemyAircrafts);
        //  画英雄机
        canvas.drawBitmap(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight(), null);

        //  画生命和得分
        paintLifeAndScore();

        //通过unlockCanvasAndPost(mCanvas)方法对画布内容进行提交
        mSurfaceHolder.unlockCanvasAndPost(canvas);
    }

    private void paintLifeAndScore()
    {
        //  画生命和得分
        mPaint.setFakeBoldText(true);
        mPaint.setTextSize(55);
        mPaint.setColor(Color.RED);
        canvas.drawText("SCORE : "+score,0,50,mPaint);
        canvas.drawText("LIFE : "+heroAircraft.getHp(),0,100,mPaint);
    }

    @Override
    public void run() {
        //设置一个循环来绘制，通过标志位来控制开启绘制还是停止
        while (mbLoop){

            //  加载敌机
            loadAirCrafts();

            //  加载子弹
            loadBullets();

            //  敌机移动
            aircraftsMoveAction();

            //  子弹移动
            bulletsMoveAction();

            //  碰撞检测
            crashCheckAction();

            //  去掉失效的道具、飞机、子弹
            postProcessAction();

            //  画图
            synchronized (mSurfaceHolder){
                draw();
            }

            //  判断是否结束
            isEnd();
        }
    }


    //  传入集合，画飞机
    //  如无意外、不必在改。总教练不必关心这种小事
    private void paintImageWithPositionRevised(Canvas canvas, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }
        for(int i=0;i<objects.size();++i){
            Bitmap image = objects.get(i).getImage();
            assert image!=null : objects.getClass().getName()+"has no image";
            canvas.drawBitmap(image,objects.get(i).getLocationX()-image.getWidth()/2,objects.get(i).getLocationY(),mPaint);
        }
    }


    //  不变的代码 计算飞机air是否应该产生 并相应更新计时器
    //  大哥也不用管。传参就完了。
    protected boolean timeCountAndNewCycleJudge(String air) {
        Double t = airCycle.get(air);
        airCycle.put(air,t+airInterval);
        Double duration = airDuration.get(air);
        if (airCycle.get(air) >= duration && airCycle.get(air) - airInterval < airCycle.get(air)) {
            // 跨越到新的周期
            airCycle.put(air,airCycle.get(air)%duration);
            return true;
        } else {
            return false;
        }
    }

    //  加载敌机 大哥不用管
    protected void loadAirCrafts()
    {
        // 周期性执行（控制频率）
        if (timeCountAndNewCycleJudge("normal")) {
            // 新敌机产生
            if (enemyAircrafts.size() < enemyMaxNumber) {
                enemyAircrafts.add(new MobEnemyFactory().createAircraft(30,0,15,1));
            }
        }
        else if(timeCountAndNewCycleJudge("elite"))
        {
            // 新敌机产生
            if (enemyAircrafts.size() < enemyMaxNumber) {
                enemyAircrafts.add(new EliteEnemyFactory().createAircraft(80,5,13,2));
            }
        }
        System.out.println("Before");
        if(isBoss()){
            System.out.println("cntBoss " + cntBoss);
            System.out.println("isBoss true");
            if(enemyAircrafts.size()<enemyMaxNumber){
                Log.d("Boss","biu biu");
                enemyAircrafts.add(new BossEnemyFactory().createAircraft(bossHp,bosssX,bosssY,bossNum));
            }
        }
    }

    //  飞机移动  大哥不用管
    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    //   子弹移动  大哥不用管
    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            if(bullet!=null){
                bullet.forward();
            }
        }
    }


    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // TODO 敌机子弹攻击英雄
        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)) {
                // 英雄机撞击到敌军子弹
                // 英雄机损失一定生命值 子弹消失、英雄小于0则死亡
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }
        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        if(enemyAircraft instanceof BossEnemy){
                            --cntBoss;
                        }
                        // TODO 获得分数，产生道具补给  交给大哥了！
                        score += 10;
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();

                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // Todo: 我方获得道具，道具生效  交给大哥了！
        for(AbstractReward reward : leftRewards)
        {
            //  ...
        }
    }


    /**
     * 大哥不用管
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 检查英雄机生存
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            enemyBullets.removeIf(AbstractFlyingObject::notValid);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            heroBullets.removeIf(AbstractFlyingObject::notValid);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        }
    }

    //  大哥不用管 加载子弹（精英机和英雄机）
    public void loadBullets()
    {
        for (String key : shootCycle.keySet()) {
            shootCycle.put(key,shootCycle.get(key)+ShootInterval);
            Double st = shootCycle.get(key);
            Double dur = shootDuration.get(key);
            if(st>=dur){
                if("enemy".equals(key)) {
                    enemyShootAction();
                }
                else{
                    heroShootAction();
                }
                shootCycle.put(key,st%dur);
            }
        }
    }

    //  敌机射击 大哥不用管
    private void enemyShootAction() {
        for (AbstractAircraft airEnemy : enemyAircrafts) {
            enemyBullets.addAll(airEnemy.shoot());
        }
    }

    // 英雄射击 大哥不用管
    private void heroShootAction() {
        heroBullets.addAll(heroAircraft.shoot());
    }

    //  判断游戏是否结束
    public void isEnd()
    {
//        totalTime += airInterval; totalTime 总计时间 用于难度升级
        if (heroAircraft.getHp() <= 0) {
            surfaceDestroyed(mSurfaceHolder);
            // 游戏结束 bgm停止；结束音乐开始
//            if (MusicThread.getIsMusic()) {
//                MusicThread.setIsEnd(true);
//                new MusicThread("src/videos/game_over.wav", false, true).start();
//            }
            System.out.println("Game Over!");
        }
    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        new Thread(this).start();
    }
    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        screenWidth = width;
        screenHeight = height;
    }
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        mbLoop = false;
    }


    protected boolean isBoss()
    {
        System.out.println("cntBoss " + cntBoss);
        //  同一时刻只有一个Boss机
        if(cntBoss>=bossLimit) {
            return false;
        }
        //  是否又该出现Boss机
        int t = score / bossThreshold;
        if(t<=recordOfBoss) {
            return false;
        }
        recordOfBoss = t;           //  更新
        ++cntBoss;
        return true;
    }

}
