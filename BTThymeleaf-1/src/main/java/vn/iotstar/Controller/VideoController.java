package vn.iotstar.Controller;


import vn.iotstar.Entity.Video;
import vn.iotstar.Service.CategoryService;
import vn.iotstar.Service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.util.StringUtils;
import java.util.List;

@Controller
@RequestMapping("admin/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;
    @Autowired
    private CategoryService categoryService; // Cần CategoryService để hiển thị dropdown

    @GetMapping("add")
    public String addVideo(ModelMap model) {
        model.addAttribute("video", new Video()); // Đối tượng Video rỗng
        model.addAttribute("categories", categoryService.findAll()); // Lấy danh sách category để hiển thị dropdown
        return "admin/videos/add";
    }

    @PostMapping("saveOrUpdate")
    public ModelAndView saveOrUpdateVideo(@ModelAttribute("video") Video video, ModelMap model) {
        videoService.save(video);
        model.addAttribute("message", "Video saved successfully!");
        return new ModelAndView("redirect:/admin/videos", model);
    }

    @RequestMapping("") // Trang danh sách video
    public String listVideos(ModelMap model) {
        List<Video> videos = videoService.findAll();
        model.addAttribute("videos", videos);
        return "admin/videos/list";
    }

    @GetMapping("edit/{videoId}")
    public String editVideo(@PathVariable("videoId") int videoId, ModelMap model) {
        Video video = videoService.findById(videoId).orElse(null);
        if (video != null) {
            model.addAttribute("video", video);
            model.addAttribute("categories", categoryService.findAll()); // Lấy danh sách category cho dropdown
            return "admin/videos/edit"; // Sử dụng form tương tự add
        }
        model.addAttribute("message", "Video not found!");
        return "redirect:/admin/videos";
    }

    @GetMapping("delete/{videoId}")
    public ModelAndView deleteVideo(@PathVariable("videoId") int videoId, ModelMap model) {
        videoService.deleteById(videoId);
        model.addAttribute("message", "Video is deleted!");
        return new ModelAndView("redirect:/admin/videos", model);
    }

    @RequestMapping("search")
    public String searchVideos(ModelMap model,
                               @RequestParam(name = "title", required = false) String title,
                               @RequestParam(name = "categoryName", required = false) String categoryName) {
        List<Video> videos = null;

        if (StringUtils.hasText(title) && StringUtils.hasText(categoryName)) {
            // Tìm kiếm theo cả tiêu đề và tên category
            videos = videoService.findByCategory_CategoryNameContaining(categoryName); // Lọc category trước
            // Sau đó lọc tiếp theo tiêu đề nếu có
            if (StringUtils.hasText(title)) {
                videos.removeIf(v -> !v.getTitle().toLowerCase().contains(title.toLowerCase()));
            }
        } else if (StringUtils.hasText(title)) {
            // Tìm kiếm theo tiêu đề
            videos = videoService.findByTitleContaining(title);
        } else if (StringUtils.hasText(categoryName)) {
            // Tìm kiếm theo tên category
            videos = videoService.findByCategory_CategoryNameContaining(categoryName);
        } else {
            // Nếu không có điều kiện tìm kiếm, hiển thị tất cả
            videos = videoService.findAll();
        }

        model.addAttribute("videos", videos);
        model.addAttribute("searchTitle", title);
        model.addAttribute("searchCategoryName", categoryName);
        return "admin/videos/search"; // Hoặc hiển thị trên trang list
    }
}