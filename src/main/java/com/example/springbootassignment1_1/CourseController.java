package com.example.springbootassignment1_1;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.springbootassignment1_1.model.Course;
import com.example.springbootassignment1_1.service.CourseService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    // Main dashboard page (works at http://localhost:8082/courses)
    @GetMapping
    public String getCoursesHtml() {
        StringBuilder html = new StringBuilder();
        html.append("""
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Bit by Bit | UFH Computer Science Management System</title>
            <script src="https://cdn.tailwindcss.com"></script>
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
            <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
            <style>
                @import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap');
                body { font-family: 'Inter', system-ui, sans-serif; }
                .dashboard-card { transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1); }
                .dashboard-card:hover { transform: translateY(-4px); box-shadow: 0 20px 25px -5px rgb(0 0 0 / 0.1), 0 8px 10px -6px rgb(0 0 0 / 0.1); }
            </style>
        </head>
        <body class="bg-gray-50">
            <!-- Top Navbar -->
            <nav class="bg-[#003087] text-white shadow-lg sticky top-0 z-50">
                <div class="max-w-7xl mx-auto px-6 py-4 flex items-center justify-between">
                    <div class="flex items-center gap-3">
                        <i class="fas fa-graduation-cap text-3xl"></i>
                        <div>
                            <h1 class="text-2xl font-semibold">Bit by Bit</h1>
                            <p class="text-xs text-blue-200 -mt-1">Management System</p>
                        </div>
                        <span class="ml-4 text-sm bg-white/20 px-3 py-1 rounded-full">UFH Computer Science</span>
                    </div>
                    <div class="flex items-center gap-6">
                        <button onclick="window.location.href='/courses/api'" 
                                class="flex items-center gap-2 bg-white text-[#003087] hover:bg-blue-100 px-5 py-2.5 rounded-xl font-medium transition">
                            <i class="fas fa-database"></i>
                            <span>CRUD API</span>
                        </button>
                        <div class="w-9 h-9 bg-white/20 rounded-2xl flex items-center justify-center text-xl">👨‍🏫</div>
                        <div class="text-sm">
                            <p class="font-medium">Gareth • Lecturer Mode</p>
                            <p class="text-xs text-blue-200">Online</p>
                        </div>
                    </div>
                </div>
            </nav>

            <div class="flex min-h-screen">
                <!-- Sidebar -->
                <div class="w-64 bg-white border-r shadow-sm">
                    <div class="p-6">
                        <a href="/courses" class="flex items-center gap-3 px-4 py-3 bg-blue-50 text-[#003087] rounded-2xl font-medium mb-2">
                            <i class="fas fa-tachometer-alt"></i>
                            <span>Dashboard</span>
                        </a>
                        <a href="/courses/all-courses" class="flex items-center gap-3 px-4 py-3 hover:bg-gray-100 rounded-2xl text-gray-700 transition">
                            <i class="fas fa-book"></i>
                            <span>All Courses</span>
                        </a>
                        <a href="/courses/manage" class="flex items-center gap-3 px-4 py-3 hover:bg-gray-100 rounded-2xl text-gray-700 transition">
                            <i class="fas fa-cogs"></i>
                            <span>Manage Courses</span>
                        </a>
                        <a href="/courses/analytics" class="flex items-center gap-3 px-4 py-3 hover:bg-gray-100 rounded-2xl text-gray-700 transition">
                            <i class="fas fa-chart-bar"></i>
                            <span>Analytics</span>
                        </a>
                    </div>
                </div>

                <!-- Main Content -->
                <div class="flex-1 p-8">
                    <div class="max-w-7xl mx-auto">
                        <!-- Hero -->
                        <div class="mb-10">
                            <h1 class="text-5xl font-semibold text-gray-900">Computer Science Department</h1>
                            <p class="text-2xl text-gray-600 mt-2">University of Fort Hare • Academic Year 2026</p>
                        </div>

                        <!-- Stats Cards -->
                        <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-12">
                            <div class="bg-gradient-to-br from-emerald-500 to-emerald-600 rounded-2xl p-6 text-white shadow-lg">
                                <i class="fas fa-seedling text-4xl mb-3"></i>
                                <h3 class="text-2xl font-bold" id="foundationCount">0</h3>
                                <p class="text-emerald-100">Foundation Courses</p>
                            </div>
                            <div class="bg-gradient-to-br from-blue-500 to-blue-600 rounded-2xl p-6 text-white shadow-lg">
                                <i class="fas fa-graduation-cap text-4xl mb-3"></i>
                                <h3 class="text-2xl font-bold" id="undergradCount">0</h3>
                                <p class="text-blue-100">Undergraduate Courses</p>
                            </div>
                            <div class="bg-gradient-to-br from-purple-500 to-purple-600 rounded-2xl p-6 text-white shadow-lg">
                                <i class="fas fa-trophy text-4xl mb-3"></i>
                                <h3 class="text-2xl font-bold" id="honoursCount">0</h3>
                                <p class="text-purple-100">Honours Courses</p>
                            </div>
                        </div>

                        <!-- Foundation Courses -->
                        <div class="mb-12">
                            <h2 class="text-2xl font-semibold text-gray-800 mb-4 flex items-center gap-3">
                                <span class="text-emerald-600" id="foundationCountTitle">2</span> Foundation Courses
                            </h2>
                            <div class="grid grid-cols-1 md:grid-cols-2 gap-6" id="foundation">
                                <!-- Populated by JS -->
                            </div>
                        </div>

                        <!-- Undergraduate -->
                        <div class="mb-12">
                            <h2 class="text-2xl font-semibold text-gray-800 mb-4 flex items-center gap-3">
                                <span class="text-blue-600" id="undergradCountTitle">5</span> Undergraduate Courses
                            </h2>
                            <div class="grid grid-cols-1 md:grid-cols-3 gap-6" id="undergrad">
                                <!-- Populated by JS -->
                            </div>
                        </div>

                        <!-- Honours -->
                        <div>
                            <h2 class="text-2xl font-semibold text-gray-800 mb-4 flex items-center gap-3">
                                <span class="text-purple-600" id="honoursCountTitle">4</span> Honours Courses
                            </h2>
                            <div class="grid grid-cols-1 md:grid-cols-2 gap-6" id="honours">
                                <!-- Populated by JS -->
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <script>
                // Load courses from API
                async function loadCourses() {
                    try {
                        const response = await fetch('/courses/api');
                        const courses = await response.json();
                        
                        // Render courses by level
                        renderCourses(courses, "Foundation", "foundation");
                        renderCourses(courses, "Undergraduate", "undergrad");
                        renderCourses(courses, "Honours", "honours");
                        
                        // Update counts
                        updateCounts(courses);
                    } catch (error) {
                        console.error('Error loading courses:', error);
                    }
                }

                function renderCourses(courses, level, containerId) {
                    const container = document.getElementById(containerId);
                    const filtered = courses.filter(c => c.level === level);
                    container.innerHTML = filtered.map(course => `
                        <div class="dashboard-card bg-white border border-gray-200 rounded-3xl p-6 shadow-sm hover:border-blue-300">
                            <div class="flex justify-between items-start">
                                <div>
                                    <span class="px-3 py-1 text-xs font-medium bg-blue-100 text-blue-700 rounded-2xl">${course.courseCode}</span>
                                    <h3 class="text-xl font-semibold text-gray-900 mt-3">${course.name}</h3>
                                </div>
                                <i class="fas fa-book text-4xl text-gray-300"></i>
                            </div>
                            <p class="text-gray-500 text-sm mt-4">${course.description || 'No description available'}</p>
                        </div>
                    `).join('');
                }

                function updateCounts(courses) {
                    const foundation = courses.filter(c => c.level === "Foundation").length;
                    const undergrad = courses.filter(c => c.level === "Undergraduate").length;
                    const honours = courses.filter(c => c.level === "Honours").length;
                    
                    document.getElementById('foundationCount').textContent = foundation;
                    document.getElementById('undergradCount').textContent = undergrad;
                    document.getElementById('honoursCount').textContent = honours;
                    document.getElementById('foundationCountTitle').textContent = foundation;
                    document.getElementById('undergradCountTitle').textContent = undergrad;
                    document.getElementById('honoursCountTitle').textContent = honours;
                }

                // Load on page load
                loadCourses();
            </script>
        </body>
        </html>
        """);

        return html.toString();
    }

    // ALL COURSES page - List all courses in a table view
    @GetMapping("/all-courses")
    public String getAllCoursesPage() {
        return """
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>All Courses | Bit by Bit</title>
            <script src="https://cdn.tailwindcss.com"></script>
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
            <style>
                @import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap');
                body { font-family: 'Inter', system-ui, sans-serif; }
            </style>
        </head>
        <body class="bg-gray-50">
            <nav class="bg-[#003087] text-white shadow-lg sticky top-0 z-50">
                <div class="max-w-7xl mx-auto px-6 py-4 flex items-center justify-between">
                    <div class="flex items-center gap-3">
                        <i class="fas fa-graduation-cap text-3xl"></i>
                        <div>
                            <h1 class="text-2xl font-semibold">Bit by Bit</h1>
                            <p class="text-xs text-blue-200 -mt-1">Management System</p>
                        </div>
                    </div>
                    <div class="flex items-center gap-6">
                        <a href="/courses" class="text-white hover:text-blue-200 transition">Dashboard</a>
                        <a href="/courses/manage" class="text-white hover:text-blue-200 transition">Manage</a>
                        <a href="/courses/analytics" class="text-white hover:text-blue-200 transition">Analytics</a>
                    </div>
                </div>
            </nav>

            <div class="max-w-7xl mx-auto px-6 py-8">
                <div class="mb-8">
                    <h1 class="text-4xl font-bold text-gray-900">All Courses</h1>
                    <p class="text-gray-600 mt-2">Complete list of all computer science courses</p>
                </div>

                <div class="bg-white rounded-2xl shadow-sm overflow-hidden">
                    <div class="overflow-x-auto">
                        <table class="min-w-full divide-y divide-gray-200">
                            <thead class="bg-gray-50">
                                <tr>
                                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">ID</th>
                                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Course Code</th>
                                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Course Name</th>
                                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Level</th>
                                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Description</th>
                                </tr>
                            </thead>
                            <tbody id="coursesTableBody" class="bg-white divide-y divide-gray-200">
                                <tr><td colspan="5" class="text-center py-8">Loading courses...</td></tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <script>
                async function loadAllCourses() {
                    try {
                        const response = await fetch('/courses/api');
                        const courses = await response.json();
                        const tbody = document.getElementById('coursesTableBody');
                        
                        if (courses.length === 0) {
                            tbody.innerHTML = '<tr><td colspan="5" class="text-center py-8">No courses found</td></tr>';
                            return;
                        }
                        
                        tbody.innerHTML = courses.map(course => `
                            <tr class="hover:bg-gray-50">
                                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">${course.id}</td>
                                <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">${course.courseCode}</td>
                                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">${course.name}</td>
                                <td class="px-6 py-4 whitespace-nowrap">
                                    <span class="px-2 py-1 text-xs rounded-full ${
                                        course.level === 'Foundation' ? 'bg-emerald-100 text-emerald-700' :
                                        course.level === 'Undergraduate' ? 'bg-blue-100 text-blue-700' :
                                        'bg-purple-100 text-purple-700'
                                    }">${course.level}</span>
                                </td>
                                <td class="px-6 py-4 text-sm text-gray-500">${course.description || '-'}</td>
                            </tr>
                        `).join('');
                    } catch (error) {
                        console.error('Error:', error);
                        document.getElementById('coursesTableBody').innerHTML = '<tr><td colspan="5" class="text-center py-8 text-red-500">Error loading courses</td></tr>';
                    }
                }
                
                loadAllCourses();
            </script>
        </body>
        </html>
        """;
    }

    // MANAGE COURSES page - Full CRUD interface
    @GetMapping("/manage")
    public String manageCoursesPage() {
        return """
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Manage Courses | Bit by Bit</title>
            <script src="https://cdn.tailwindcss.com"></script>
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
            <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
            <style>
                @import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap');
                body { font-family: 'Inter', system-ui, sans-serif; }
                .modal-transition { transition: all 0.3s ease; }
            </style>
        </head>
        <body class="bg-gray-50">
            <nav class="bg-[#003087] text-white shadow-lg sticky top-0 z-50">
                <div class="max-w-7xl mx-auto px-6 py-4 flex items-center justify-between">
                    <div class="flex items-center gap-3">
                        <i class="fas fa-graduation-cap text-3xl"></i>
                        <div>
                            <h1 class="text-2xl font-semibold">Bit by Bit</h1>
                            <p class="text-xs text-blue-200 -mt-1">Course Management</p>
                        </div>
                    </div>
                    <div class="flex items-center gap-6">
                        <a href="/courses" class="text-white hover:text-blue-200 transition">Dashboard</a>
                        <a href="/courses/all-courses" class="text-white hover:text-blue-200 transition">All Courses</a>
                        <a href="/courses/analytics" class="text-white hover:text-blue-200 transition">Analytics</a>
                    </div>
                </div>
            </nav>

            <div class="max-w-7xl mx-auto px-6 py-8">
                <div class="flex justify-between items-center mb-8">
                    <div>
                        <h1 class="text-4xl font-bold text-gray-900">Manage Courses</h1>
                        <p class="text-gray-600 mt-2">Create, edit, or delete courses</p>
                    </div>
                    <button onclick="openCourseModal()" 
                            class="bg-emerald-500 hover:bg-emerald-600 px-6 py-3 rounded-xl font-medium transition flex items-center gap-2 text-white">
                        <i class="fas fa-plus"></i>
                        <span>Add New Course</span>
                    </button>
                </div>

                <!-- Search -->
                <div class="bg-white rounded-2xl shadow-sm p-4 mb-8">
                    <div class="relative">
                        <i class="fas fa-search absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
                        <input type="text" id="searchInput" onkeyup="filterCourses()" 
                               placeholder="Search by course name or code..." 
                               class="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-xl focus:outline-none focus:border-[#003087]">
                    </div>
                </div>

                <!-- Courses Grid -->
                <div id="coursesGrid" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                    <!-- Courses loaded here -->
                </div>
            </div>

            <!-- Add/Edit Modal -->
            <div id="courseModal" class="fixed inset-0 bg-black bg-opacity-50 hidden items-center justify-center z-50 modal-transition">
                <div class="bg-white rounded-3xl w-full max-w-2xl mx-4">
                    <div class="p-6 border-b">
                        <h2 id="modalTitle" class="text-2xl font-bold text-gray-900">Add New Course</h2>
                        <button onclick="closeCourseModal()" class="absolute top-6 right-6 text-gray-400 hover:text-gray-600">
                            <i class="fas fa-times text-2xl"></i>
                        </button>
                    </div>
                    <form id="courseForm" class="p-6 space-y-4">
                        <input type="hidden" id="courseId">
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-2">Course Code *</label>
                            <input type="text" id="courseCode" required class="w-full px-4 py-2 border border-gray-300 rounded-xl">
                        </div>
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-2">Course Name *</label>
                            <input type="text" id="courseName" required class="w-full px-4 py-2 border border-gray-300 rounded-xl">
                        </div>
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-2">Level *</label>
                            <select id="courseLevel" required class="w-full px-4 py-2 border border-gray-300 rounded-xl">
                                <option value="">Select Level</option>
                                <option value="Foundation">Foundation</option>
                                <option value="Undergraduate">Undergraduate</option>
                                <option value="Honours">Honours</option>
                            </select>
                        </div>
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-2">Description</label>
                            <textarea id="courseDescription" rows="4" class="w-full px-4 py-2 border border-gray-300 rounded-xl"></textarea>
                        </div>
                        <div class="flex gap-3 pt-4">
                            <button type="submit" class="flex-1 bg-[#003087] text-white py-2 rounded-xl hover:bg-[#002266] transition font-medium">
                                Save Course
                            </button>
                            <button type="button" onclick="closeCourseModal()" class="flex-1 bg-gray-200 text-gray-700 py-2 rounded-xl hover:bg-gray-300 transition">
                                Cancel
                            </button>
                        </div>
                    </form>
                </div>
            </div>

            <script>
                let allCourses = [];
                let searchTerm = '';

                async function loadCourses() {
                    try {
                        const response = await fetch('/courses/api');
                        allCourses = await response.json();
                        renderCourses();
                    } catch (error) {
                        console.error('Error:', error);
                    }
                }

                function renderCourses() {
                    let filtered = allCourses;
                    if (searchTerm) {
                        filtered = allCourses.filter(c => 
                            c.name.toLowerCase().includes(searchTerm) || 
                            c.courseCode.toLowerCase().includes(searchTerm)
                        );
                    }

                    const grid = document.getElementById('coursesGrid');
                    if (filtered.length === 0) {
                        grid.innerHTML = '<div class="col-span-full text-center py-12 text-gray-500">No courses found</div>';
                        return;
                    }

                    grid.innerHTML = filtered.map(course => `
                        <div class="bg-white rounded-2xl shadow-sm border border-gray-200 overflow-hidden">
                            <div class="p-6">
                                <div class="flex justify-between items-start mb-4">
                                    <div>
                                        <span class="inline-block px-3 py-1 text-xs font-medium rounded-xl 
                                            ${course.level === 'Foundation' ? 'bg-emerald-100 text-emerald-700' : 
                                              course.level === 'Undergraduate' ? 'bg-blue-100 text-blue-700' : 
                                              'bg-purple-100 text-purple-700'}">
                                            ${course.level}
                                        </span>
                                        <p class="text-sm text-gray-500 mt-2">${course.courseCode}</p>
                                    </div>
                                    <div class="flex gap-2">
                                        <button onclick="editCourse(${course.id})" class="text-blue-600 hover:bg-blue-50 p-2 rounded-lg">
                                            <i class="fas fa-edit"></i>
                                        </button>
                                        <button onclick="deleteCourse(${course.id})" class="text-red-600 hover:bg-red-50 p-2 rounded-lg">
                                            <i class="fas fa-trash"></i>
                                        </button>
                                    </div>
                                </div>
                                <h3 class="text-xl font-semibold text-gray-900 mb-2">${course.name}</h3>
                                <p class="text-gray-600 text-sm">${course.description || 'No description'}</p>
                            </div>
                        </div>
                    `).join('');
                }

                function filterCourses() {
                    searchTerm = document.getElementById('searchInput').value.toLowerCase();
                    renderCourses();
                }

                function openCourseModal(course = null) {
                    const modal = document.getElementById('courseModal');
                    document.getElementById('courseForm').reset();
                    
                    if (course) {
                        document.getElementById('modalTitle').innerText = 'Edit Course';
                        document.getElementById('courseId').value = course.id;
                        document.getElementById('courseCode').value = course.courseCode;
                        document.getElementById('courseName').value = course.name;
                        document.getElementById('courseLevel').value = course.level;
                        document.getElementById('courseDescription').value = course.description || '';
                    } else {
                        document.getElementById('modalTitle').innerText = 'Add New Course';
                        document.getElementById('courseId').value = '';
                    }
                    
                    modal.classList.remove('hidden');
                    modal.classList.add('flex');
                }

                function closeCourseModal() {
                    const modal = document.getElementById('courseModal');
                    modal.classList.remove('flex');
                    modal.classList.add('hidden');
                }

                async function editCourse(id) {
                    try {
                        const response = await fetch(`/courses/api/${id}`);
                        const course = await response.json();
                        openCourseModal(course);
                    } catch (error) {
                        Swal.fire('Error', 'Failed to load course', 'error');
                    }
                }

                async function deleteCourse(id) {
                    const result = await Swal.fire({
                        title: 'Are you sure?',
                        text: "This action cannot be undone!",
                        icon: 'warning',
                        showCancelButton: true,
                        confirmButtonColor: '#d33',
                        cancelButtonColor: '#3085d6',
                        confirmButtonText: 'Yes, delete it!'
                    });
                    
                    if (result.isConfirmed) {
                        try {
                            const response = await fetch(`/courses/api/${id}`, { method: 'DELETE' });
                            if (response.ok) {
                                Swal.fire('Deleted!', 'Course has been deleted.', 'success');
                                loadCourses();
                            }
                        } catch (error) {
                            Swal.fire('Error', 'Failed to delete course', 'error');
                        }
                    }
                }

                document.getElementById('courseForm').addEventListener('submit', async (e) => {
                    e.preventDefault();
                    
                    const id = document.getElementById('courseId').value;
                    const courseData = {
                        courseCode: document.getElementById('courseCode').value,
                        name: document.getElementById('courseName').value,
                        level: document.getElementById('courseLevel').value,
                        description: document.getElementById('courseDescription').value
                    };
                    
                    try {
                        let response;
                        if (id) {
                            response = await fetch(`/courses/api/${id}`, {
                                method: 'PUT',
                                headers: { 'Content-Type': 'application/json' },
                                body: JSON.stringify(courseData)
                            });
                            if (response.ok) Swal.fire('Updated!', 'Course updated.', 'success');
                        } else {
                            response = await fetch('/courses/api', {
                                method: 'POST',
                                headers: { 'Content-Type': 'application/json' },
                                body: JSON.stringify(courseData)
                            });
                            if (response.ok) Swal.fire('Created!', 'New course added.', 'success');
                        }
                        
                        closeCourseModal();
                        loadCourses();
                    } catch (error) {
                        Swal.fire('Error', 'Operation failed', 'error');
                    }
                });

                loadCourses();
            </script>
        </body>
        </html>
        """;
    }

    // ANALYTICS page
    @GetMapping("/analytics")
    public String analyticsPage() {
        return """
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Analytics | Bit by Bit</title>
            <script src="https://cdn.tailwindcss.com"></script>
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
            <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
            <style>
                @import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap');
                body { font-family: 'Inter', system-ui, sans-serif; }
            </style>
        </head>
        <body class="bg-gray-50">
            <nav class="bg-[#003087] text-white shadow-lg sticky top-0 z-50">
                <div class="max-w-7xl mx-auto px-6 py-4 flex items-center justify-between">
                    <div class="flex items-center gap-3">
                        <i class="fas fa-graduation-cap text-3xl"></i>
                        <div>
                            <h1 class="text-2xl font-semibold">Bit by Bit</h1>
                            <p class="text-xs text-blue-200 -mt-1">Analytics Dashboard</p>
                        </div>
                    </div>
                    <div class="flex items-center gap-6">
                        <a href="/courses" class="text-white hover:text-blue-200 transition">Dashboard</a>
                        <a href="/courses/all-courses" class="text-white hover:text-blue-200 transition">All Courses</a>
                        <a href="/courses/manage" class="text-white hover:text-blue-200 transition">Manage</a>
                    </div>
                </div>
            </nav>

            <div class="max-w-7xl mx-auto px-6 py-8">
                <div class="mb-8">
                    <h1 class="text-4xl font-bold text-gray-900">Analytics Dashboard</h1>
                    <p class="text-gray-600 mt-2">Course statistics and insights</p>
                </div>

                <!-- Stats Overview -->
                <div class="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
                    <div class="bg-white rounded-2xl shadow-sm p-6">
                        <div class="flex items-center justify-between">
                            <div>
                                <p class="text-gray-500 text-sm">Total Courses</p>
                                <p id="totalCourses" class="text-3xl font-bold text-gray-900">0</p>
                            </div>
                            <i class="fas fa-book text-4xl text-blue-500"></i>
                        </div>
                    </div>
                    <div class="bg-white rounded-2xl shadow-sm p-6">
                        <div class="flex items-center justify-between">
                            <div>
                                <p class="text-gray-500 text-sm">Foundation Level</p>
                                <p id="foundationTotal" class="text-3xl font-bold text-emerald-600">0</p>
                            </div>
                            <i class="fas fa-seedling text-4xl text-emerald-500"></i>
                        </div>
                    </div>
                    <div class="bg-white rounded-2xl shadow-sm p-6">
                        <div class="flex items-center justify-between">
                            <div>
                                <p class="text-gray-500 text-sm">Undergraduate</p>
                                <p id="undergradTotal" class="text-3xl font-bold text-blue-600">0</p>
                            </div>
                            <i class="fas fa-graduation-cap text-4xl text-blue-500"></i>
                        </div>
                    </div>
                    <div class="bg-white rounded-2xl shadow-sm p-6">
                        <div class="flex items-center justify-between">
                            <div>
                                <p class="text-gray-500 text-sm">Honours</p>
                                <p id="honoursTotal" class="text-3xl font-bold text-purple-600">0</p>
                            </div>
                            <i class="fas fa-trophy text-4xl text-purple-500"></i>
                        </div>
                    </div>
                </div>

                <!-- Charts -->
                <div class="grid grid-cols-1 lg:grid-cols-2 gap-8 mb-8">
                    <div class="bg-white rounded-2xl shadow-sm p-6">
                        <h3 class="text-xl font-semibold text-gray-900 mb-4">Course Distribution by Level</h3>
                        <canvas id="distributionChart" height="300"></canvas>
                    </div>
                    <div class="bg-white rounded-2xl shadow-sm p-6">
                        <h3 class="text-xl font-semibold text-gray-900 mb-4">Recent Activity</h3>
                        <div id="activityLog" class="space-y-3">
                            <p class="text-gray-500 text-center py-8">Loading statistics...</p>
                        </div>
                    </div>
                </div>

                <!-- Course List with Details -->
                <div class="bg-white rounded-2xl shadow-sm p-6">
                    <h3 class="text-xl font-semibold text-gray-900 mb-4">Course Breakdown</h3>
                    <div id="courseBreakdown" class="space-y-4">
                        <!-- Dynamic content -->
                    </div>
                </div>
            </div>

            <script>
                let distributionChart = null;

                async function loadAnalytics() {
                    try {
                        const response = await fetch('/courses/api');
                        const courses = await response.json();
                        
                        // Calculate statistics
                        const foundation = courses.filter(c => c.level === "Foundation");
                        const undergrad = courses.filter(c => c.level === "Undergraduate");
                        const honours = courses.filter(c => c.level === "Honours");
                        
                        // Update stats
                        document.getElementById('totalCourses').textContent = courses.length;
                        document.getElementById('foundationTotal').textContent = foundation.length;
                        document.getElementById('undergradTotal').textContent = undergrad.length;
                        document.getElementById('honoursTotal').textContent = honours.length;
                        
                        // Create chart
                        if (distributionChart) {
                            distributionChart.destroy();
                        }
                        const ctx = document.getElementById('distributionChart').getContext('2d');
                        distributionChart = new Chart(ctx, {
                            type: 'doughnut',
                            data: {
                                labels: ['Foundation', 'Undergraduate', 'Honours'],
                                datasets: [{
                                    data: [foundation.length, undergrad.length, honours.length],
                                    backgroundColor: ['#10b981', '#3b82f6', '#8b5cf6'],
                                    borderWidth: 0
                                }]
                            },
                            options: {
                                responsive: true,
                                maintainAspectRatio: true,
                                plugins: {
                                    legend: {
                                        position: 'bottom'
                                    }
                                }
                            }
                        });
                        
                        // Activity log
                        const activityLog = document.getElementById('activityLog');
                        activityLog.innerHTML = `
                            <div class="flex items-center justify-between p-3 bg-gray-50 rounded-lg">
                                <div><i class="fas fa-database text-blue-500"></i> Total Courses in System</div>
                                <div class="font-bold">${courses.length}</div>
                            </div>
                            <div class="flex items-center justify-between p-3 bg-gray-50 rounded-lg">
                                <div><i class="fas fa-chart-line text-green-500"></i> Average per Level</div>
                                <div class="font-bold">${(courses.length / 3).toFixed(1)} courses</div>
                            </div>
                            <div class="flex items-center justify-between p-3 bg-gray-50 rounded-lg">
                                <div><i class="fas fa-percent text-purple-500"></i> Foundation %</div>
                                <div class="font-bold">${((foundation.length / courses.length) * 100).toFixed(1)}%</div>
                            </div>
                        `;
                        
                        // Course breakdown by level
                        const breakdown = document.getElementById('courseBreakdown');
                        breakdown.innerHTML = `
                            <div class="border rounded-lg p-4">
                                <h4 class="font-semibold text-emerald-600 mb-2">Foundation Courses (${foundation.length})</h4>
                                <div class="space-y-1">
                                    ${foundation.map(c => `<div class="text-sm"><span class="font-medium">${c.courseCode}</span> - ${c.name}</div>`).join('')}
                                    ${foundation.length === 0 ? '<div class="text-sm text-gray-500">No foundation courses</div>' : ''}
                                </div>
                            </div>
                            <div class="border rounded-lg p-4">
                                <h4 class="font-semibold text-blue-600 mb-2">Undergraduate Courses (${undergrad.length})</h4>
                                <div class="space-y-1">
                                    ${undergrad.map(c => `<div class="text-sm"><span class="font-medium">${c.courseCode}</span> - ${c.name}</div>`).join('')}
                                    ${undergrad.length === 0 ? '<div class="text-sm text-gray-500">No undergraduate courses</div>' : ''}
                                </div>
                            </div>
                            <div class="border rounded-lg p-4">
                                <h4 class="font-semibold text-purple-600 mb-2">Honours Courses (${honours.length})</h4>
                                <div class="space-y-1">
                                    ${honours.map(c => `<div class="text-sm"><span class="font-medium">${c.courseCode}</span> - ${c.name}</div>`).join('')}
                                    ${honours.length === 0 ? '<div class="text-sm text-gray-500">No honours courses</div>' : ''}
                                </div>
                            </div>
                        `;
                        
                    } catch (error) {
                        console.error('Error:', error);
                    }
                }
                
                loadAnalytics();
            </script>
        </body>
        </html>
        """;
    }

    // PART B - CRUD JSON endpoints with Validation
    @GetMapping("/api")
    public List<Course> getAll() {
        return courseService.getAllCourses();
    }

    @GetMapping("/api/{id}")
    public ResponseEntity<Course> getById(@PathVariable Long id) {
        return courseService.getCourseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/api")
    public ResponseEntity<Course> create(@Valid @RequestBody Course course) {
        return ResponseEntity.ok(courseService.createCourse(course));
    }

    @PutMapping("/api/{id}")
    public ResponseEntity<Course> update(@PathVariable Long id, @Valid @RequestBody Course course) {
        Course updated = courseService.updateCourse(id, course);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/api/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return courseService.deleteCourse(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}