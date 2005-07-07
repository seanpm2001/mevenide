package org.mevenide.idea.project.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.mevenide.idea.Res;
import org.mevenide.idea.execute.MavenExecuteManager;
import org.mevenide.idea.project.PomManager;
import org.mevenide.idea.project.goals.Goal;
import org.mevenide.idea.project.ui.PomManagerPanel;
import org.mevenide.idea.project.util.PomUtils;
import org.mevenide.idea.util.actions.AbstractAnAction;
import org.mevenide.idea.util.ui.images.Icons;

/**
 * @author Arik
 */
public class ExecuteGoalAction extends AbstractAnAction {
    /**
     * Resources
     */
    private static final Res RES = Res.getInstance(ExecuteGoalAction.class);

    private static final String DLG_TITLE = RES.get("exec.goal.dlg.title");
    private static final String DLG_LABEL = RES.get("exec.goal.dlg.label");

    public ExecuteGoalAction() {
        super(RES.get("exec.goal.action.name"),
              RES.get("exec.goal.action.desc"),
              Icons.EXECUTE);
    }

    @Override
    public void update(final AnActionEvent pEvent) {
        final Project project = getProject(pEvent);
        if (project == null) {
            pEvent.getPresentation().setEnabled(false);
            return;
        }

        final PomManager pomMgr = PomManager.getInstance(project);
        if (PomManagerPanel.TITLE.equals(pEvent.getPlace())) {
            final PomManagerPanel ui = pomMgr.getToolWindowComponent();
            if (ui == null) {
                pEvent.getPresentation().setEnabled(false);
                return;
            }

            final Goal[] goals = ui.getSelectedGoals();
            pEvent.getPresentation().setEnabled(
                    goals != null && goals.length > 0);
        }
    }

    public void actionPerformed(final AnActionEvent pEvent) {
        final Project project = getProject(pEvent);
        if (project == null) {
            pEvent.getPresentation().setEnabled(false);
            return;
        }

        final PomManager pomMgr = PomManager.getInstance(project);

        if (PomManagerPanel.TITLE.equals(pEvent.getPlace())) {
            final PomManagerPanel ui = pomMgr.getToolWindowComponent();
            if (ui == null)
                return;

            final Goal[] goals = ui.getSelectedGoals();
            if (goals == null || goals.length == 0)
                return;

            final String[] poms = ui.getPomsWithSelectedGoals(false);
            final String url = PomUtils.selectPom(project, poms, DLG_TITLE, DLG_LABEL);
            if (url == null || url.trim().length() == 0)
                return;

            final VirtualFile file = pomMgr.getFile(url);
            if (file == null || !file.isValid())
                return;

            MavenExecuteManager.getInstance(project).execute(file, goals);
        }
    }
}
