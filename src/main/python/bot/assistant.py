# pip install -q -U google-genai
import sys

sys.stdout.reconfigure(encoding='utf-8')
import json
from google import genai

client = genai.Client(api_key="")
messages = []
TRANSLATION_PROMPT = """
    You are a professional translator.  
    When given text with a locale in the last of sentence, translate it to the specified language.  
    Always return the full translation in the requested language and keep the syntax of the input sentence. 
    Do not return with locale.
    Example: "message:Hello, age:19, location:Namdinh, jp" → "message:'こんにちは', age:19, location:'ナムディン'".
    
    **Note**: 
    - If you receive a property names 'note', it means taking note something.
    - If you receive a property names 'statusName' with value 'Accepted offer', it means manager has accepted the salary's offer.
    - If you receive a property names 'statusName' with value 'Approved offer', it means manager has approved the salary's offer.
    - If you receive a property names 'statusName' with value 'Cancelled', it means manager has canceled the salary's offer.
    - If you receive a property names 'statusName' with value 'Declined offer', it means manager has declined the salary's offer.
    - If you receive a property names 'statusName' with value 'Rejected offer', it means manager has rejected the salary's offer.
    - If you receive a property that contains value 'Offer', it means the salary's offer.
    - If you receive a property names 'statusName' with value 'Waiting for approval', it means recruiter is waiting for his manager to accept salary's offer.
    - If you receive a property names 'name' with value 'Business analysis', it means the process of examining an organization's needs, problems, and opportunities to propose effective solutions.
    - If you receive a property names 'name' with value 'Communication', it refers to the process of creating, sharing, and distributing information through various media channels. In Vietnamese, it means **'Truyền thông'**.
    - If you receive a property names 'name' with value 'Tester', it refers to a person who evaluates and verifies the functionality, performance, and quality of software or systems by identifying bugs and issues.
"""
# TRANSLATION_PROMPT += "\nNote: If you receive a property names 'note', it means taking note something."
messages = [{"role": "system", "content": TRANSLATION_PROMPT}]

def chat_with_ollama(user_input):
    messages.append(
        {
            "role": "user",
            "content": user_input
        }
    )
    response = client.models.generate_content(
        model="gemini-2.0-flash",
        contents=[m["content"] for m in messages]
    )
    return json.dumps(response.text.strip(), ensure_ascii=False)


if __name__ == "__main__":
    user_input = sys.argv[1]
    print(chat_with_ollama(user_input))
