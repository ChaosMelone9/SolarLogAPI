/*
Copyright 2020 - 2021 Christoph Kohnen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package me.meloni.SolarLogAPI.BasicGUI.Components;

import javax.swing.*;

/**
 * This class provides a method to invoke a simple JFrame with one component
 * @author Christoph Kohnen
 * @since 3.3.0
 */
public class SimpleFrame extends JFrame {
    /**
     * Get a simple frame to display a {@link JComponent}
     * @param jComponent The component which the frame should show
     * @param exitOnClose Whether or not the process should quit upon closing the window
     */
    public SimpleFrame(JComponent jComponent, Boolean exitOnClose) {
        add(jComponent);

        if(exitOnClose) {
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
        setSize(400,800);
        setVisible(true);
        setLocationRelativeTo(null);
    }
}
