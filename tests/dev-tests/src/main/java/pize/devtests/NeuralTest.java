package pize.devtests;

import pize.math.Maths;

import java.util.Arrays;

public class NeuralTest{
    
    class LastLayer{
        
        float[] neurons;
        
        LastLayer(int size){
            neurons = new float[size];
        }
        
        void print(){
            if(neurons.length == 1){
                System.out.println(neurons[0]);
                return;
            }
            System.out.println(Arrays.toString(neurons));
        }
        
        void set(float... neurons){
            System.arraycopy(neurons, 0, this.neurons, 0, neurons.length);
        }
        
        void sigmoid(){
            int shift = 1;
            for(int i = 0; i < neurons.length; i++)
                neurons[i] = Maths.sigmoid(neurons[i] + shift);
        }
        
    }
    
    class Layer extends LastLayer{
        
        float[] weights;
        
        Layer(int size){
            super(size);
            weights = new float[size];
        }
        
        void mulTo(LastLayer layer){
            Maths.mul(neurons, weights, layer.neurons);
            layer.sigmoid();
        }
        
        void setWeights(float... weights){
            System.arraycopy(weights, 0, this.weights, 0, weights.length);
        }
        
        void backprop(Layer prevLayer, float[] outData){
            for(int i = 0; i < outData.length; i++){
                float error = neurons[i] - outData[i];
                
            }
        }
        
    }
    
    
    class Network{
    
        Layer in;
        Layer[] hidden;
        LastLayer out;
        
        Network(int hiddenLayersNum){
            hidden = new Layer[hiddenLayersNum];
        }
        
        
        Layer layer(int index){
            return hidden[index];
        }
        
        void def(int index, int size){
            hidden[index + 1] = new Layer(size);
        }
        
        void defIn(int size){
            in = new Layer(size);
        }
        
        void defOut(int size){
            out = new LastLayer(size);
        }
        
        void process(){
            if(hidden.length == 0){
                in.mulTo(out);
                return;
            }
            
            int last = hidden.length - 1;
            in.mulTo(hidden[0]);
            for(int i = 0; i < last; i++)
                hidden[i].mulTo(hidden[i + 1]);
            hidden[last].mulTo(out);
        }
        
        
    
    }
    
    
    NeuralTest(){
        Network network = new Network(0);
        network.defIn(3);
        network.defOut(1);
        
        network.in.set(1, 0, -0.5F);
        network.in.setWeights(10, 0, -5);
        network.process();
        network.out.print();
    }
    
}
