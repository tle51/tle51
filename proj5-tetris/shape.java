
/**
  * The {@code shape} class is responsible for creating 
  * different tetris shapes, it will always give
  * back the shape as coordinates on a 2D grid
  */
public class shape implements T_shape {
  /* The tag for what shape this is*/
  private int t_shape;
  /*Array that holds the points that represent the shape*/
  private XY[] shape_array;
  /*Point of rotation for the shape*/
  private int piv_pt;
  
/**
  * Creates a shape and set the coordinate.
  * @param number, the shape that needs to be created.
  */
  public shape(int number){
    t_shape = number;
    switch(t_shape){
      case 1:
        shape_array = line_f();
        break;
      case 2:
        shape_array = square_f();
        break;
      case 3:
        shape_array = Lshape_f();
        break;
      case 4:
        shape_array = Jshape_f();
        break;
      case 5:
        shape_array = Tshape_f();
        break;
      case 6:
        shape_array = Zshape_f();
        break;
      case 7 : 
        shape_array = Sshape_f();
      default :
        shape_array = Sshape_f();
    }
  }
 
/**
  * Get number of squares the shape contains.
  * @return number of squares the shape contains.
  */
  public int shape_size(){
   return 4; 
  }
  
 
/**
  * Get the type of shape.
  * @return the number corresponding to the shape.
  */
  public int the_shape(){
   return t_shape; 
  }
  
 /**
  * Rotate (right)the coordinates of the current shape with respect to
  * the point of rotation, does not change the current values in the current shape.
  * @return  the rotated coordinates of the shape.
  */
  public XY[] rotate_right(){
    /*Do not rotate square*/
    if(the_shape() == square){
     return shape_array; 
    }
    
    XY[] tmp = new XY[4];
    /*Rotate each point with respect to the point of rotation (right)*/
    for(int i=0; i < shape_array.length; i++){
      if(shape_array[i].piv != true){
       int tmp1 = shape_array[i].x_c - shape_array[piv_pt].x_c; 
       int tmp2 = shape_array[i].y_c - shape_array[piv_pt].y_c;
       int x = (-1)*tmp2;
       int y = tmp1;
       tmp[i] = new XY();
       tmp[i].x_c = shape_array[piv_pt].x_c + x;
       tmp[i].y_c = shape_array[piv_pt].y_c +y;
       tmp[i].piv = shape_array[i].piv;
      }
    }
    tmp[piv_pt] = new XY();
    tmp[piv_pt].x_c = shape_array[piv_pt].x_c;
    tmp[piv_pt].y_c = shape_array[piv_pt].y_c;
    tmp[piv_pt].piv = true;
    return tmp;
  }
  
/**
  * Rotate (left)the coordinates of the current shape with respect to
  * the point of rotation, does not change the current values in the current shape.
  * @return  the rotated coordinates of the shape.
  */
  public XY[] rotate_left(){
   /*Do not rotate square*/
    if(the_shape() == square){
     return shape_array; 
    }

    XY[] tmp = new XY[4]; 
  /*Rotate each point with respect to the point of rotation (left)*/
    for(int i=0; i < shape_array.length; i++){
      if(shape_array[i].piv != true){
       int tmp1 = shape_array[i].x_c - shape_array[piv_pt].x_c; 
       int tmp2 = shape_array[i].y_c - shape_array[piv_pt].y_c;
       int x = tmp2;
       int y = (-1)*tmp1;
       tmp[i] = new XY();
       tmp[i].x_c = shape_array[piv_pt].x_c + x;
       tmp[i].y_c = shape_array[piv_pt].y_c +y;
       tmp[i].piv = shape_array[i].piv;
       
      }
    }
    tmp[piv_pt] = new XY();
    tmp[piv_pt].x_c = shape_array[piv_pt].x_c;
    tmp[piv_pt].y_c = shape_array[piv_pt].y_c;
    tmp[piv_pt].piv = true;
    return tmp;
  }
  
/**
  * Get current shape coordinates.
  * @return coordinates of the shape.
  */
  public XY[] get_shape_cord(){
   return shape_array; 
  }
  
/**
  * Get current shape coordinates.
  * @parm c_shape, new coordinates for the shape.
  */
  public void set_shape_cord(XY[] c_shape){
    shape_array = c_shape;
   
  }
 
/**
  * Creates the line shape
  */
  private XY[] line_f(){
    XY[] tmp = new XY[4];
    tmp[0] = new XY(3,0);
    tmp[0].piv = false;
    tmp[1] = new XY(4,0);
    tmp[1].piv = false;
    tmp[2] = new XY(5,0);
    tmp[2].piv = true;
    piv_pt = 2;
    tmp[3] = new XY(6,0);
    tmp[3].piv = false;
    return tmp;
  }
 /**
  * Creates the L shape
  */
  private XY[] Lshape_f(){
    XY[] tmp = new XY[4];
    tmp[0] = new XY(4,1);
    tmp[0].piv = false;
    tmp[1] = new XY(5,1);
    tmp[1].piv = true;
    tmp[2] = new XY(6,1);
    tmp[2].piv = false;
    
    tmp[3] = new XY(6,0);
    tmp[3].piv = false;
    piv_pt = 1;
    return tmp;
  }
/**
  * Creates the square shape
  */
    private XY[] square_f(){
    XY[] tmp = new XY[4];
    tmp[0] = new XY(4,0);
    tmp[0].piv = false;
    tmp[1] = new XY(4,1);
    tmp[1].piv = true;
    tmp[2] = new XY(5,0);
    tmp[2].piv = false;
    tmp[3] = new XY(5,1);
    tmp[3].piv = false;
    piv_pt = 1;
    return tmp;
  }
    
/**
  * Creates the J shape
  */
   private XY[] Jshape_f(){
    XY[] tmp = new XY[4];
    tmp[0] = new XY(3,1);
    tmp[0].piv = false;
    tmp[1] = new XY(4,1);
    tmp[1].piv = true;
    tmp[2] = new XY(5,1);
    tmp[2].piv = false;    
    tmp[3] = new XY(3,0);
    tmp[3].piv = false;
    piv_pt = 1;
    return tmp;
  }
/**
  * Creates the T shape
  */  
   private XY[] Tshape_f(){
    XY[] tmp = new XY[4];
    tmp[0] = new XY(4,0);
    tmp[0].piv = false;
    tmp[1] = new XY(4,1);
    tmp[1].piv = true;
    tmp[2] = new XY(3,1);
    tmp[2].piv = false;
    tmp[3] = new XY(5,1);
    tmp[3].piv = false;
    piv_pt = 1;
    return tmp;
  }
/**
  * Creates the Z shape
  */  
   private XY[] Zshape_f(){
    XY[] tmp = new XY[4];
    tmp[0] = new XY(4,0);
    tmp[0].piv = false;
    tmp[1] = new XY(5,0);
    tmp[1].piv = false;
    tmp[2] = new XY(5,1);
    tmp[2].piv = true;  
    tmp[3] = new XY(6,1);
    tmp[3].piv = false;
    piv_pt = 2;
    return tmp;
  }
   
/**
  * Creates the S shape
  */
   private XY[] Sshape_f(){
    XY[] tmp = new XY[4];
    tmp[0] = new XY(5,0);
    tmp[0].piv = false;
    tmp[1] = new XY(6,0);
    tmp[1].piv = false;
    tmp[2] = new XY(4,1);
    tmp[2].piv = true;    
    tmp[3] = new XY(5,1);
    tmp[3].piv = false;
    piv_pt = 2;
    return tmp;
  }
/**
  * move the shape coordinates down one 
  */   
   public XY[] move_down_one(){
     XY[] tmp = new XY[4];
     for(int i =0; i< 4; i++){
      tmp[i]= new XY();
      tmp[i].y_c = shape_array[i].y_c +1;  
      tmp[i].x_c = shape_array[i].x_c;
      tmp[i].piv = shape_array[i].piv;
     }
     return tmp; 
   }

}
