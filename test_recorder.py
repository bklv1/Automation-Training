from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.action_chains import ActionChains
from selenium.common.exceptions import NoSuchWindowException
import time
import atexit

def run_test_recorder():
    driver = None
    page_clicks_map = {}
    page_hovers_map = {}

    def cleanup():
        print("\nShutdown hook triggered. Printing recorded elements...")
        print_recorded_elements(page_clicks_map, page_hovers_map)
        if driver:
            driver.quit()

    atexit.register(cleanup)
    # Initialize WebDriver
    chrome_options = Options()
    chrome_options.add_argument("--remote-allow-origins=*")
    chrome_options.add_argument("--start-maximized")
    chrome_options.add_argument("--disable-extensions")
    chrome_options.add_argument("--disable-popup-blocking")
    chrome_options.add_experimental_option("excludeSwitches", ["enable-automation"])
    driver = webdriver.Chrome(options=chrome_options)

    # Open the initial page
    driver.get("https://opensource-demo.orangehrmlive.com/")

    # Inject JavaScript to listen for click and hover events
    inject_listeners(driver)

    # Maps to store the list of clicked and hovered elements for each URL
    page_clicks_map = {}
    page_hovers_map = {}

    # Monitor URL changes and re-inject JavaScript
    current_url = driver.current_url
    wait = WebDriverWait(driver, 10)

    # Initialize the lists for the initial page
    page_clicks_map[current_url] = []
    page_hovers_map[current_url] = []

    while True:
        try:
            # Check if URL has changed
            if current_url != driver.current_url:
                current_url = driver.current_url
                # Wait for the new page to load
                wait.until(EC.presence_of_element_located((By.TAG_NAME, "body")))
                # Re-inject JavaScript on the new page
                inject_listeners(driver)

                # Initialize the lists for the new page if not already present
                page_clicks_map.setdefault(current_url, [])
                page_hovers_map.setdefault(current_url, [])

            # Retrieve the HTML of the last clicked element
            clicked_element_html = driver.execute_script("return window.clickedElementHtml;")
            if clicked_element_html and clicked_element_html not in page_clicks_map[current_url]:
                page_clicks_map[current_url].append(clicked_element_html)
                print(f"Clicked Element: {clicked_element_html}")

            # Retrieve the HTML of the last hovered element
            hovered_element_html = driver.execute_script("return window.hoveredElementHtml;")
            if hovered_element_html and hovered_element_html not in page_hovers_map[current_url]:
                page_hovers_map[current_url].append(hovered_element_html)
                print(f"Hovered Element: {hovered_element_html}")

            # Sleep for a short duration to prevent excessive CPU usage
            time.sleep(1)

        except Exception:
            # The browser window was closed, exit the loop
            break

    return page_clicks_map, page_hovers_map

def print_recorded_elements(page_clicks_map, page_hovers_map):
    print("\nAll Clicked Elements by Page:")
    for url, elements in page_clicks_map.items():
        if elements:
            print(f"Page URL: {url}")
            for element_html in elements:
                print(f" - {element_html}")

    print("\nAll Hovered Elements by Page:")
    for url, elements in page_hovers_map.items():
        if elements:
            print(f"Page URL: {url}")
            for element_html in elements:
                print(f" - {element_html}")

def inject_listeners(driver):
    script = """
    var hoverTimeout;
    document.addEventListener('click', function(event) {
        var element = event.target;
        window.clickedElementHtml = element.outerHTML;
        console.log('Element clicked: ' + window.clickedElementHtml);
    }, true);
    document.addEventListener('mouseover', function(event) {
        var element = event.target;
        hoverTimeout = setTimeout(function() {
            window.hoveredElementHtml = element.outerHTML;
            console.log('Element hovered for 5 seconds: ' + window.hoveredElementHtml);
        }, 5000);  // 5 seconds
    }, true);
    document.addEventListener('mouseout', function(event) {
        clearTimeout(hoverTimeout);
    }, true);
    """
    driver.execute_script(script)

if __name__ == "__main__":
    run_test_recorder()
