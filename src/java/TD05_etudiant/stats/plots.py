#!/usr/bin/env python
# -*- coding: utf-8 -*-
#-----------------------------------------------------------------------------

import os ,sys

import numpy as np
import matplotlib.pyplot as plt

from scipy.stats import ks_2samp
from scipy.stats import mannwhitneyu


default_params = {
    'font'        : {'family' : 'serif', 'size' : 10},
    'size'        : (10, 5),
    'dpi'         : 100,
    'xlabel'      : 'xlabel' ,
    'ylabel'      : 'ylabel',
    'logscale'    : 'n', 
    'label'       : '',
    'title'       : '', 
    'quartiles'   : True,
    'quart-color' : 'lightgreen',
    'mean-color'  : 'blue',
    'color'       : '#9999ff',
    'cmap'        : 'BuPu',
    'cbar_shrink' : 1,
    'cbar_orientation' : 'horizontal',
    'alpha'       : 0.75,
    'aspect'      : None,
    'hist_bins'   : 50,
    'print_values' : True
}

def stat_test(x, y, ks=False):
    """
    Perform a tow sample statistical test returns a p value 
    """
    if ks:
        return ks_2samp(x,y)
    else :
        return mannwhitneyu(x,y)
    
def stars(p):
    """
    Return statistical significance marker 
    a sting of starts depending on the p-value

    """
    
    if p < 0.0001:
        return "****"
    elif (p < 0.001):
        return "***"
    elif (p < 0.01):
        return "**"
    elif (p < 0.05):
        return "*"
    else:
        return "-"
    
def draw_stars(d1, d2, ax, i, j):

    y_max = max(concatenate((d1, d2)))
    y_min = min(concatenate((d1, d2)))
    z,p = stat_test(d1, d2)

    ax.annotate("", 
                xy=(i+1, y_max),     xycoords='data',
                xytext=(j+1, y_max), textcoords='data',
                arrowprops=dict(arrowstyle="-", ec='#aaaaaa',
                                connectionstyle="bar,fraction=0.2"))

    ax.text((j-i)+0.5, y_max + abs(y_max - y_min)*0.3, stars(p*2.0),
            horizontalalignment='center',
            verticalalignment='center')
                


    
def process_parameters(parameters=None):
    params = {}
    for p in default_params :
        params[p] = default_params[p]
    
    if not parameters is None :
        for p in parameters:
            params[p] = parameters[p]
    return params


def percentiles( data ):
    """
    Computes median and quartiles 
    Takes a 2d array 
    
    Returns 5 1d arrays 

    """

    data = data.T

    length = np.shape(data)[0]
    
    median  = np.zeros(length)
    perc_05 = np.zeros(length)
    perc_25 = np.zeros(length)
    perc_75 = np.zeros(length)
    perc_95 = np.zeros(length)
    
    for i in xrange(length) : 
        median  [i] = np.median(     data[i] )
        perc_25 [i] = np.percentile( data[i], 25)
        perc_75 [i] = np.percentile( data[i], 75)
        perc_05 [i] = np.percentile( data[i], 5 )
        perc_95 [i] = np.percentile( data[i], 95)

    return  perc_05, perc_25, median, perc_75, perc_95
    


def read_file(fname) :
    """ 
    read a data file and returns a numpy 2d array 

    [ [ evaluation ... ] , [ best fitness ... ] , [ diversity ... ] 

    """

    return np.loadtxt(fname, usecols=(0, 1, 4), unpack=True)


def find_files(path, start='', end=''):
    """
    Return a list of all files in the path whose name starts with start
    and ends with end

    """
    ls  = os.listdir(path)
    ret = []
    for entry in ls :
        if entry.startswith(start) and entry.endswith(end):
            ret.append(path+'/'+entry)
    return ret


def process_data(path):
    """
    Read the data file in path and 
    return 3 arrays 

    E : 1d array of evaluations numbers 
    F : 2d array of fitness values of each files 
    D : 2d array of diversity values of each file
    """

    files = find_files(path, end='.out')

    E = []
    F = []
    D = []
    E, f, d = read_file(files[0])
    l = len(E)
    F.append(f)
    D.append(d)
    for f in files[1:] :
        e, f, d = read_file(f)
        F.append(f)
        D.append(d)
        if l < len(e) :
            l = len(e)
            E = e
            
    # pad arrays to have same size 
    for i in xrange(len(F)) :
        if len(F[i]) < len(E) :
            F[i] = np.append(F[i], [F[i][-1]] * (l-len(F[i])) )
            D[i] = np.append(D[i], [D[i][-1]] * (l-len(D[i])) )
            
    return E, np.asarray(F), np.asarray(D)


def plot_shaded_plot(x, y, parameters=None, fname=None):
    """

    Plot boxplot for set of data (median and shaded quartiles)
    where data is a list of lists of values 
    
    data = [ [...] , ... , [...] ] 
    
    """
    
    params = process_parameters(parameters)
    
    plt.rc('font', **params['font'])
    
    
    perc_05, perc_25, med, perc_75, perc_95 = percentiles(y)

    fig = plt.figure(num=None, figsize=params['size'], dpi=params['dpi'])
    axis = plt.subplot(111)
       
    
    plt.clf()      
    if params['quartiles'] :
        plt.fill_between(x, perc_25, perc_75,
                          alpha=0.5, linewidth=0.1,
                          color=params['quart-color'])
        
        plt.fill_between(x, perc_75, perc_95,
                          alpha=0.25, linewidth=0.1,
                          color=params['quart-color'])
        
        plt.fill_between(x, perc_05, perc_25,
                          alpha=0.25, linewidth=0.1,
                          color=params['quart-color'])
        
    plt.plot(x, med, lw=1, alpha=1.0,
              label=params['label'],
              color=params['quart-color'])
        
    plt.grid(axis='y', color="0.9", linestyle='-', linewidth=1)
    axis.spines['top'].set_visible(False)
    axis.spines['right'].set_visible(False)
    axis.spines['left'].set_visible(False)
    axis.get_xaxis().tick_bottom()
    axis.get_yaxis().tick_left()
    axis.tick_params(axis='x', direction='out')
    axis.tick_params(axis='y', length=0)
    for spine in axis.spines.values():
        spine.set_position(('outward', 5))
    axis.set_axisbelow(True)
    axis.set_xlabel(params['xlabel'])
    axis.set_ylabel(params['ylabel'])

    if params['logscale'] == 'y' :
        plt.set_yscale('log')
    if params['logscale'] == 'x' :
        plt.set_xscale('log')

    # draw & show / save image
    if fname is None:
        plt.draw()
        plt.show()
    else: 
        plt.savefig(fname)



def plot_many_curves( data, parameters=None, fname=None):
    """
    Plot many curve on the given axis with the given parameters
    
    """

    params = process_parameters(parameters)
    
    plt.rc('font', **params['font'])
    fig = plt.figure(num=None, figsize=params['size'], dpi=params['dpi'])

    fig.clf()

    axis = plt.subplot(111)
    
   
    for d in data :
        plt.plot(d[0], d[1], lw=1,  label=d[2])
        
            
    plt.grid(axis='y', color="0.9",  linestyle='-', linewidth=1)
    axis.spines['top'].set_visible(False)
    axis.spines['right'].set_visible(False)
    axis.spines['left'].set_visible(False)

    axis.get_xaxis().tick_bottom()
    axis.get_yaxis().tick_left()
    axis.tick_params(axis='x', direction='out')
    axis.tick_params(axis='y', length=0)
    for spine in axis.spines.values():
        spine.set_position(('outward', 5))
    axis.set_axisbelow(True)
    axis.set_xlabel(params['xlabel'])
    axis.set_ylabel(params['ylabel'])
    
    if params['logscale'] == 'y' :
        plt.set_yscale('log')
    if params['logscale'] == 'x' :
        plt.set_xscale('log')

   
    plt.legend(loc='upper right', frameon=False)

        
    # draw & show / save image
    if fname is None:
        plt.draw()
        plt.show()
    else: 
        plt.savefig(fname)

def compare_algorithms(paths):
    """
    prepare data to draw multiple plots of different algorithms

    """
    fit = []
    div = []
    for p in paths : 
        E, F, D  = process_data(p)
        _, _, med, _, _ = percentiles(F)
        fit.append ( ( E, med, p) )
        _, _, med, _, _ = percentiles(D)
        div.append ( ( E, med, p) )
    
    plot_many_curves(fit, fname='fit.png',
                     parameters = {'xlabel' : 'Evaluations',
                                   'ylabel' : 'Fitness'} )
    plot_many_curves(div, fname='div.png',
                     parameters = {'xlabel' : 'Evaluations',
                                   'ylabel' : 'Diversity'} )

    
if __name__ == '__main__':

    if len(sys.argv) < 2 :
        print 'Usage :'+ sys.argv[0] + ' path1 [path2] ...'
        print '\tpath1     : the directory to the data files'
        sys.exit(0)

    for p in sys.argv[1:] : 
        E, F, D  = process_data(p)
        plot_shaded_plot(E, F, fname=p+"/fit.png",
                         parameters = {'xlabel' : 'Evaluations',
                                       'ylabel' : 'Fitness'} )
        plot_shaded_plot(E, D, fname=p+"/div.png",
                         parameters = {'xlabel' : 'Evaluations',
                                       'ylabel' : 'Diversity'})

    # if multiple data paths, compare algorithms 
    if len(sys.argv) > 2 :
        compare_algorithms(sys.argv[1:])

        
