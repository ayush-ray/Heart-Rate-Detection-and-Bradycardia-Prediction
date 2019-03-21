%% Removing High-Frequency Noise from an ECG Signal
% This  shows how to lowpass filter an ECG signal that contains high frequency 
% noise.

[header, recorddata] = edfread('14-27-59.EDF');
x=transpose(recorddata);
x=x(4500000:5000000,1);
%disp(x);
%x=x-mean(x(:));
x=x/std(x(:));
%disp(header);
%disp(recorddata);
%x=x(:,3);
%disp(x);
%disp(size(x));
y = sgolayfilt(x,0,3);
%disp("Y starts");
%disp(y);
Fs = 1000;
[M,N] = size(y);
%disp(M);
%disp(N);

% Initialize scopes
TS = dsp.TimeScope('SampleRate',Fs,...
                      'TimeSpan',1.5,...
                      'YLimits',[-1 1],...
                      'ShowGrid',true,...
                      'NumInputPorts',2,...
                      'LayoutDimensions',[2 1],...
                      'Title','Noisy and Filtered Signals');

% Design lowpass filter
Fpass  = 200;
Fstop = 400;
Dpass = 0.05;
Dstop = 0.0001;
F     = [0 Fpass Fstop Fs/2]/(Fs/2);
%disp(F);
A     = [1 1     0     0];
D     = [Dpass Dstop];
b = firgr('minorder', F, A, D);
%disp(b);
LP = dsp.FIRFilter('Numerator',b);

% Design Highpass Filter
Fstop = 200;
Fpass = 400;
Dstop = 0.0001;
Dpass = 0.05;
F = [0 Fstop Fpass Fs/2]/(Fs/2); % Frequency vector

A = [0 0     1     1]; % Amplitude vector
D = [Dstop   Dpass];   % Deviation (ripple) vector
b  = firgr('minord', F, A, D);
HP = dsp.FIRFilter('Numerator', b);

% Stream
tic;

if toc < 30
   
    x = .1 * randn(M,N);
    highFreqNoise = HP(x);
    noisySignal    = y + highFreqNoise;
    filteredSignal = LP(noisySignal);
    x=filteredSignal;
   
end
[Rpeak_index Rpeak_values] = RPeakDetection(x);
%disp("Rpeak"+Rpeak_values);
disp("Rpeak_index"+Rpeak_index);
%plot(x);
 
 upflag=0;
 p=0;
 count1=0;
 count2=0;
 locationsOfBradycardia=[];
 iter=1;
 pulse=zeros(length(Rpeak_values),1);
 for i = 2:length(Rpeak_values)
         if(upflag==0)
                t = Rpeak_index(i)-Rpeak_index(i-1);
                p=(250*60)/(t);
   
     upflag=100;
    else
        if (upflag>0)
             upflag=upflag-1;
        end
    end
    pulse(i)=p;     
  
   end
    if pulse(i)<60
        locationsOfBradycardia=[locationsOfBradycardia;[Rpeak_index(i) pulse(i)]];
        count1=count1+1;
        %iter=iter+1;
    elseif pulse(i)>=60
        
        locationsOfBradycardia=[locationsOfBradycardia;[Rpeak_index(i) pulse(i)]];
          count2=count2+1;
        %iter=iter+1;
    end
    
end

t=(0:length(pulse)-1)/250;

disp(locationsOfBradycardia);    
csvwrite('myFile.txt',locationsOfBradycardia);
disp(pulse);
plot(t,pulse);
title('HeartRate with respect to time')
ylabel('Heart Rate')
xlabel('Time')




