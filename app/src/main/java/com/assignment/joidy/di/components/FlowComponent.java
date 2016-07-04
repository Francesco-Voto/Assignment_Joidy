package com.assignment.joidy.di.components;


import com.assignment.joidy.di.modules.FlowModule;
import com.assignment.joidy.di.scopes.Flow;
import com.assignment.joidy.ui.MainActivity;

import dagger.Component;

@Flow
@Component(dependencies = AppComponent.class, modules = FlowModule.class)
public interface FlowComponent {

    void inject(MainActivity mainActivity);
}
