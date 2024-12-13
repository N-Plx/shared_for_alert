# example.groovy
On ifarm, load the standard clas12 environment or at least modules to use groovy.

The code should be ran with CJ updated with the new geometry service. For now you can use:

>/work/clas12/pilleux/alert/atof_reconstruction/coatjava/coatjava/bin/run-groovy example.groovy

It will produce a txt file to be read as you like. 

x_i,y_i,z_i are the coordinates for each of the 8 vertices ("corners") of each scintillator trapezoide.

# run some simulations with the updated geometry
Waiting for a release, on ifarm you can run some simple simulations

> cd /w/hallb-scshelf2102/clas12/pilleux/alert/clas12Tags-5.10/source
> ./gemc your_options

Some first options to try: 
> ./gemc alert_protongun.gcard -SAVE_ALL_MOTHERS=1 -SKIPREJECTEDHITS=1 -NGENP=50 -INTEGRATEDRAW="*" -USE_GUI=0 -RUNNO=11 -N=10

- This gcard specifies we use gemc's internal event generator, it's a proton gun. If you want to use a LUND file instead the generator options will be ignored. 
- Several of these options are for truth matching, can be removed. 
- Replace -N=10 with any number of events you want to generate. 
