# Goto Editor 
This little plugin helps you to

1. Browse resources with the 'Explorer' of your platform from Eclipse package explorer or resource navigators

2. Open selected file with your system editor(customable).

3. Open shell in current selected file path.

Just select a resource and launch the [Goto ...] menu item from this resources context pop-up menu.

You can setup your preferred browser or finder program in Eclipse preferences.

This plugin has been tested on win32, linux and MacOSX platforms.

This product includes software developed by the EasyStruts project team (http://easystruts.sf.net).

I add new function to it you can not only "Explorer" but alsa open a terminal
,and edit the selected File out of Eclipse with your preferred editor after you
finished edit the file out of Eclipse and closed the editor (like emacs)
,Eclipse will refresh the project and it will refresh each 30s before the editor
is closed.

# screenshot
![Goto Editor](https://raw.githubusercontent.com/emacs-java/eclipse_goto_editor/master/screenshots/screenshot1.jpg)
![Goto Editor](https://raw.githubusercontent.com/emacs-java/eclipse_goto_editor/master/screenshots/screenshot2.gif)

# Install
https://github.com/emacs-java/eclipse_goto_editor/raw/master/org.jf.gotoeditor.site/plugins/org.jf.gotoeditor_1.3.5.jar 

you can download this jar ,and put it into eclipse/dropin directory
or update from

http://emacs-java.github.io/eclipse_goto_editor/update-site/

for old eclipse you can download 

https://github.com/emacs-java/eclipse_goto_editor/raw/master/org.jf.gotoeditor.site/plugins/org.jf.gotoeditor_1.3.4.jar 
  
# keybinding 
使用方式   |           快捷键 
---------- | -------------------
 editor    |         Ctrl+c e 
 terminal  |         Ctrl+c c 
 explore   |         Ctrl+c f 

另外点工具栏上的四个图标(其中一个留给你自定义命令)
右键有一个Go..菜单，里面有四个选项
点菜单栏上的Navigate

本人<jixiuf@gmail.com>下载了easyexplore1.0.4 的源代码(http://sourceforge.net/projects/easystruts/files/)，
然后重现添加了一些功能，这些功能在windows 上或许没太大用处，但是在Linux用处或许会很大
实现的功能,
    1 使用系统编辑器打开在eclipse 中选中的文件或目录（linux 上有些编辑器可以对目录进行编辑，如emacs）,
           优点是，当系统编辑器关闭时，eclipse会刷新项目所有文件,并重新编译项目。
                   如果没关闭编辑器，则每隔30s刷新一次直到关闭为止。
                   这个特点可以使许多喜欢用emacs ,vim 等编辑器的程序员更容易将之与eclipse 结合
    2 可以在选中的目录、文件处打开终端（Windows是就是cmd窗口，这个用处可能不大。linux 上那就另说了）
    3就是easyexplore 上原有的功能，用文件浏览器打开选中的目录

另有博文介绍
http://blog.csdn.net/jixiuffff/article/details/5978393
# build
  用eclipse 导入3 个项目，点击org.jf.goeditor.site里面site.xml的build all 来构建
  所有
  把site 目录下的所有,push 到gh-pages分支
 ref http://suhuanzheng7784877.iteye.com/blog/941305
