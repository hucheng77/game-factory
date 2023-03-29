package com.game.pushBox;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GameFrame extends JFrame implements ActionListener ,MouseListener,KeyListener{

    /**
     * 序列化ID，相当于身份认证，
     * 主要用于程序的版本控制，保持不同版本的兼容性，
     * 在程序版本升级时避免程序报出版本不一致的错误。
     */
    String path = new String("resources/pushBoxImg/pic");
    private static final long serialVersionUID = 1L;
    private int grade = 0;//定义关卡数
    private int row = 7,column = 7,leftX = 0,leftY = 0;
    //row,column表示人物坐标；leftX,leftY记载左上角图片位置
    private int mapRow = 0,mapColumn = 0;//地图的行列数
    private int width = 0,height = 0;//屏幕大小
    private boolean acceptKey = true;
    private Image pic[] = null;//图片数组
    private byte[][] map = null;//地图数组
    private ArrayList list = new ArrayList();//用于撤回操作
    Sound sound;

    final byte WALL = 1,BOX = 2,BOXONEND = 3,END = 4,MANDOWN = 5,
            MANLEFT = 6,MANRIGHT = 7,MANUP = 8,GRASS = 9,MANDOWNONEND = 10,
            MANLEFTONEND = 11,MANRIGHTONEND = 12,MANUPONEND = 13;
    /**
     * 构造方法
     */
    public GameFrame(){
        super("pushBox1.0版本");
        //1.窗体设置
        setSize(600,600);//窗体大小
        setVisible(true);//窗体可见
        setResizable(false);//窗体大小不可被用户改变
        setLocation(680,200);//窗体位置
        setBackground(Color.LIGHT_GRAY);//窗体背景颜色
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//窗体结束程序

        //2.cont容器设置
        Container cont = getContentPane();
        /**
         * 因为JFrame是一个框架，所以JFrame不能通过add方法直接添加组件，
         * 创建一个content pane容器，方便向框架内添加组件
         */
        cont.setLayout(null);//清空默认的流式布局管理器
        cont.setBackground(Color.green);//容器背景颜色
        getPic();//获取图片
        width = this.getWidth();
        height = this.getHeight();
        this.setFocusable(true);//设置组件可以被选中
        initMap();
        this.addKeyListener(this);
        this.addMouseListener(this);
        //播放音乐
        Sound sound = new Sound();
        sound.loadSound();//加载音乐
    }

    //初始化地图
    public void initMap(){
        map = getMap(grade);//获取每个地图的关卡数
        list.clear();//清除列表
        byte[][] temp = map;

        //创建地图
        for(int i=0;i<temp.length;i++)
        {
            for(int j=0;j<temp[0].length;j++){
                System.out.print(temp[i][j]+" ");
            }
            System.out.println();
        }

        getMapSizeAndPosition();
        getManPosition();
    }

    //获取人物当前位置
    public void getManPosition(){

        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[0].length;j++){
                if(map[i][j]==MANDOWN||map[i][j]==MANUP||map[i][j]==MANLEFT||map[i][j]==MANRIGHT){
                    row = i;
                    column = j;
                    break;
                }
            }
        }
    }


    //获取游戏区域大小及显示游戏的左上角位置
    public void getMapSizeAndPosition(){
        mapRow = map.length;
        mapColumn = map[0].length;
        leftX = (width - map[0].length * 30)/2;
        leftY = (height - map.length * 30)/2;
        System.out.println(leftX);
        System.out.println(leftY);
        System.out.println(mapRow);
        System.out.println(mapColumn);
    }

    //获取图片
    public void getPic(){
        pic = new Image[14];
        for(int i=0;i<=13;i++){
            pic[i] = Toolkit.getDefaultToolkit().getImage
                    (path+i+".png");
        }
    }

    //人走过变草地
    public byte grassOrEnd(byte man){
        byte result = GRASS;
        if(man == MANLEFTONEND || man == MANRIGHTONEND || man == MANUPONEND || man == MANDOWNONEND){
            result = END;
        }

        return result;
    }

    private void moveUp(){
        if(map[row-1][column]==WALL)
            return;
        byte tempBox;
        byte tempMan;

        if(map[row-1][column]==BOX||map[row-1][column]==BOXONEND){		//如果向上一格是箱子
            if(map[row-2][column]==GRASS||map[row-2][column]==END){     //如果向上第二格是过道或者终点
                Map currentMap = new Map(row,column,map);
                list.add(currentMap);//用于撤回操作
                tempBox = map[row-2][column]==END?BOXONEND:BOX;
                tempMan = map[row-1][column]==BOXONEND?MANUPONEND:MANUP;
                map[row][column] = grassOrEnd(map[row][column]);
                map[row-2][column] = tempBox;
                map[row-1][column] = tempMan;
                row--;
            }
        }else{//如果向上一格是过道或者终点
            Map currentMap = new Map(row,column,map);
            list.add(currentMap);//用于撤回操作
            tempMan = map[row-1][column]==GRASS?MANUP:MANUPONEND;
            map[row][column] = grassOrEnd(map[row][column]);
            map[row-1][column] = tempMan;
            row--;
        }
    }

    private void moveDown(){

        if(map[row+1][column]==WALL)
            return ;

        byte tempBox;
        byte tempMan;

        if(map[row+1][column]==BOX||map[row+1][column]==BOXONEND){
            if(map[row+2][column]==END||map[row+2][column]==GRASS){
                Map currentMap = new Map(row,column,map);
                list.add(currentMap);//用于撤回操作
                tempBox = map[row+2][column] == END?BOXONEND:BOX;
                tempMan = map[row+1][column] == BOXONEND?MANDOWNONEND:MANDOWN;
                map[row][column] = grassOrEnd(map[row][column]);
                map[row+2][column] = tempBox;
                map[row+1][column] = tempMan;
                row++;
            }
        }else{
            Map currentMap = new Map(row,column,map);
            list.add(currentMap);//用于撤回操作
            tempMan = map[row+1][column]==GRASS?MANDOWN:MANDOWNONEND;
            map[row][column] = grassOrEnd(map[row][column]);
            map[row+1][column] = tempMan;
            row++;
        }
    }

    private void moveLeft(){

        if(map[row][column-1]==WALL)
            return ;

        byte tempBox;
        byte tempMan;

        if(map[row][column-1]==BOX||map[row][column-1]==BOXONEND){
            if(map[row][column-2]==END||map[row][column-2]==GRASS){
                Map currentMap = new Map(row,column,map);
                list.add(currentMap);//用于撤回操作
                tempBox = map[row][column-2] == END?BOXONEND:BOX;
                tempMan = map[row][column-1] == BOXONEND?MANLEFTONEND:MANLEFT;
                map[row][column] = grassOrEnd(map[row][column]);
                map[row][column-2] = tempBox;
                map[row][column-1] = tempMan;
                column--;
            }
        }else{
            Map currentMap = new Map(row,column,map);
            list.add(currentMap);//用于撤回操作
            tempMan = map[row][column-1]==GRASS?MANLEFT:MANLEFTONEND;
            map[row][column] = grassOrEnd(map[row][column]);
            map[row][column-1] = tempMan;
            column--;
        }

    }

    private void moveRight(){

        if(map[row][column+1]==WALL)
            return ;

        byte tempBox;
        byte tempMan;

        if(map[row][column+1]==BOX||map[row][column+1]==BOXONEND){

            if(map[row][column+2]==END||map[row][column+2]==GRASS){

                Map currentMap = new Map(row,column,map);
                list.add(currentMap);//用于撤回操作
                tempBox = map[row][column+2] == END?BOXONEND:BOX;
                tempMan = map[row][column+1] == BOXONEND?MANRIGHTONEND:MANRIGHT;
                map[row][column] = grassOrEnd(map[row][column]);
                map[row][column+2] = tempBox;
                map[row][column+1] = tempMan;
                column++;
            }
        }else{
            Map currentMap = new Map(row,column,map);
            list.add(currentMap);//用于撤回操作
            tempMan = map[row][column+1]==GRASS?MANRIGHT:MANRIGHTONEND;
            map[row][column] = grassOrEnd(map[row][column]);
            map[row][column+1] = tempMan;
            column++;
        }
    }

    //判断游戏是否结束
    public boolean isFinished(){

        for(int i=0;i<mapRow;i++) {
            for(int j=0;j<mapColumn;j++){
                //		System.out.println("值是"+map[i][j]+",END 的值是"+END+"，他们相等吗？:"+(map[i][j]==END));
                if(map[i][j]==END||map[i][j]==MANDOWNONEND||map[i][j]==MANUPONEND||map[i][j]==MANLEFTONEND||map[i][j]==MANRIGHTONEND){
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP){
            moveUp();
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            moveDown();
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            moveLeft();
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            moveRight();
        }
        if(e.getKeyCode() == KeyEvent.VK_A){//上一关
            acceptKey = true;
            priorGrade();
            return ;
        }
        if(e.getKeyCode() == KeyEvent.VK_D){//下一关
            acceptKey = true;
            nextGrade();
            return ;
        }
        if(e.getKeyCode() == KeyEvent.VK_B){//撤回
            undo();
        }

        repaint();

        if(isFinished()){
            //禁用按键
            acceptKey = false;
            if(grade==10){JOptionPane.showMessageDialog(this, "恭喜通过最后一关");}
            else{
                String msg = "恭喜你通过第"+(grade+1)+"关！！！\n是否要进入下一关？";
                int type = JOptionPane.YES_NO_OPTION;
                String title = "过关";
                int choice = 0;
                choice = JOptionPane.showConfirmDialog(null, msg,title,type);
                if(choice==1){
                    System.exit(0);
                }else if(choice == 0){
                    acceptKey = true;
                    nextGrade();
                }
            }
        }

    }

    //画图
    public void paint(Graphics g){
        //System.out.println("我被调用了");
        for(int i=0;i<mapRow;i++) {
            for(int j=0;j<mapColumn;j++){
                if(map[i][j]!=0){
//					System.out.println("这个位置 不是0，它的值是"+map[i][j]);
//					g.drawRect(10, 30, getWidth()/2-50, getHeight()/2-50);
                    g.drawImage(pic[map[i][j]],leftX+j*30,leftY+i*30,30,30,this);
                }
            }
        }
        g.setColor(Color.black);
        g.setFont(new Font("楷体_2312",Font.BOLD,30));
        g.drawString("现在是第", 150, 140);
        g.drawString(String.valueOf(grade+1),310,140);
        g.drawString("关", 360, 140);
    }

    public int getManX(){
        return row;
    }

    public int getManY(){
        return column;
    }

    public int getGrade(){
        return grade;
    }

    public byte[][] getMap(int grade){
        return MapFactory.getMap(grade);
    }

    public void DisplayToast(String str){
        JOptionPane.showMessageDialog(null, str,"提示",JOptionPane.ERROR_MESSAGE);
    }

    //过关后不可撤销
    public void undo(){
        if(acceptKey){
            if(list.size()>0){
                Map priorMap = (Map)list.get(list.size()-1);
                map = priorMap.getMap();
                row = priorMap.getManX();
                column = priorMap.getManY();
                repaint();
                list.remove(list.size()-1);
            }else{
                DisplayToast("不能再撤销");
            }
        }else{
            DisplayToast("此关已完成，不能撤销");
        }
    }

    public void priorGrade(){
        grade--;
        acceptKey = true;
        if(grade<0)
            grade = 0;
        initMap();
        clearPaint(this.getGraphics());
        repaint();
    }

    public void nextGrade(){
        if(grade>=MapFactory.getCount()-1){
            DisplayToast("恭喜你完成所有关卡");
            acceptKey = false;
        }else{
            grade++;
            initMap();
            clearPaint(this.getGraphics());
            repaint();
            acceptKey = true;
        }
    }

    private void clearPaint(Graphics g) {
        g.clearRect(0, 0, width+leftX, height+leftY);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        if(e.getButton() == MouseEvent.BUTTON3)
        {
            undo();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }

    public static void main(String[] args){
        GameFrame a = new GameFrame();
    }
}

