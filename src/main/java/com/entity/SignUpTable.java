package com.entity;

public class SignUpTable extends Table{
    /**
     * account:info[0]
     * password:info[1]
     */
    @Override
    public void setInfo(String[] info) {
        this.info[0] = info[0];
        this.info[1] = info[1];
        this.info[2] = info[2];
    }

    public String[] getInfo(){
        return new String[]{this.info[0],this.info[1],this.info[2]};
    }
}
