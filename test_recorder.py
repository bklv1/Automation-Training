from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import time
import atexit

def initialize_driver():
    chrome_options = Options()
    chrome_options.add_argument("--remote-allow-origins=*")
    chrome_options.add_argument("--start-maximized")
    chrome_options.add_argument("--disable-extensions")
    chrome_options.add_argument("--disable-popup-blocking")
    chrome_options.add_experimental_option("excludeSwitches", ["enable-automation"])
    return webdriver.Chrome(options=chrome_options)

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

def run_test_recorder():
    driver = initialize_driver()
    page_clicks_map = {}
    page_hovers_map = {}

    def cleanup():
        print("\nShutdown hook triggered. Printing recorded elements...")
        print_recorded_elements(page_clicks_map, page_hovers_map)
        driver.quit()

    atexit.register(cleanup)

    try:
        driver.get("https://opensource-demo.orangehrmlive.com/")
        inject_listeners(driver)

        current_url = driver.current_url
        wait = WebDriverWait(driver, 10)

        page_clicks_map[current_url] = []
        page_hovers_map[current_url] = []

        while True:
            if current_url != driver.current_url:
                current_url = driver.current_url
                wait.until(EC.presence_of_element_located((By.TAG_NAME, "body")))
                inject_listeners(driver)

                page_clicks_map.setdefault(current_url, [])
                page_hovers_map.setdefault(current_url, [])

            clicked_element_html = driver.execute_script("return window.clickedElementHtml;")
            if clicked_element_html and clicked_element_html not in page_clicks_map[current_url]:
                page_clicks_map[current_url].append(clicked_element_html)
                print(f"Clicked Element: {clicked_element_html}")

            hovered_element_html = driver.execute_script("return window.hoveredElementHtml;")
            if hovered_element_html and hovered_element_html not in page_hovers_map[current_url]:
                page_hovers_map[current_url].append(hovered_element_html)
                print(f"Hovered Element: {hovered_element_html}")

            time.sleep(1)

            total_clicks = sum(len(clicks) for clicks in page_clicks_map.values())
            total_hovers = sum(len(hovers) for hovers in page_hovers_map.values())
            if total_clicks >= 10 and total_hovers >= 10:
                break

    except Exception as e:
        print(f"An error occurred: {str(e)}")

    finally:
        driver.quit()

    return page_clicks_map, page_hovers_map

if __name__ == "__main__":
    run_test_recorder()
