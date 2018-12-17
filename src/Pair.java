class Pair{
        int x,y;
        Pair(int x,int y){
            this.x=x;
            this.y=y;
        }

        public int getVar1() {
            return x;
        }

        public int getVar2() {
            return y;
        }

        @Override
        public String toString(){
            return "X="+x+" Y="+y;
        }
    }

