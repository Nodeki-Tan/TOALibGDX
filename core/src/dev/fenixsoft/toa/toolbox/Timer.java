package dev.fenixsoft.toa.toolbox;

public class Timer {

    boolean active = false;

    EventListener function;
    EventListener disableFunction;

    float time = 0f;
    float end = 1f;

    boolean loop;
    boolean overshoot = false;

    public Timer(EventListener _function,
                 EventListener _disableFunction,
                 float _time, float _end,
                 boolean _loop){

        function = _function;
        disableFunction = _disableFunction;

        time = _time;
        end = _end;

        loop = _loop;

    }

    public void tick(float delta)
    {

        if(!active) return;

        time += delta;

        if(time >= end){
            function.TriggerEvent();

            if(loop) {
                time = 0f;
            }else if(!overshoot){
                time = end;
                disable();
            }

        }

    }

    public void  reset(){
        time = 0f;
    }

    public void  enable(){
        active = true;
    }

    public void  disable(){
        active = false ;
        disableFunction.TriggerEvent();
    }

}
